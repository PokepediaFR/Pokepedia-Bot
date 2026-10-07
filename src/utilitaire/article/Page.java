/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package utilitaire.article;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utilitaire.API;
import utilitaire.Wiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Hashtable;
import java.util.Objects;

/**
 * The Page class represents a Poképedia or Bulbapedia page and it's content, making calls to the API in order to
 * get and modify its content.
 *
 * @author Mewtwo-Ex
 * @author GaletteLithium
 * @author Samuel Chanal
 */
public class Page {
	private final String title;
	private final Date touched;
	protected String content;
	private final Wiki m_from;
	
	/**
	 * Constructeur idéal pour les créations de page
	 * @param title titre de la page
	 */
	public Page(String title, Wiki from) {
		this.title = title;
		touched = new Date();
		content = null;
		m_from = from;
	}
	
	/**
	 * Constructeur pour PageCollection
	 * @param title titre de la page
	 * @param touched la date de dernière modif de la page
	 */
	public Page(String title, Date touched, Wiki from) {
		this.title = title;
		this.touched = touched;
		content = null;
		m_from = from;
	}
	
	/**
	 * Renvoie le titre de la page
	 * @return le titre de la page (sisi)
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Synchronise le backup
	 * <pre>api.php?action=query&prop=revisions&rvlimit=1&rvprop=content&format=xml&titles=MonTitre</pre>
	 * 
	 * @return le contenu textuel de la page
	 */
	public void sync() {
		getContent(false);
	}
	
	/**
	 * Fournit le contenu de la page. S'il n'a pas encore été chargé en mémoire, 
	 * on vérifie d'abord si une version à jour existe en backup, 
	 * puis on interroge l'api s'il le faut vraiment.
	 * <pre>api.php?action=query&prop=revisions&rvlimit=1&rvprop=content&format=xml&titles=MonTitre</pre>
	 * 
	 * @return le contenu textuel de la page
	 */
	public String getContent() {
		return getContent(true);
	}
	
	/**
	 * Fournit le contenu de la page. S'il n'a pas encore été chargé en mémoire, 
	 * on vérifie d'abord si une version à jour existe en backup, 
	 * puis on interroge l'api s'il le faut vraiment.
	 * <pre>api.php?action=query&prop=revisions&rvlimit=1&rvprop=content&format=xml&titles=MonTitre</pre>
	 * 
	 * @param useBackup si false, ignore le rechargement du texte depuis le backup, et récupère systématiquement depuis le wiki.
	 * 
	 * @return le contenu textuel de la page
	 */
	public String getContent(boolean useBackup) {
		if(content==null) {
			File file = new File(API.getBackupPath()+title.replaceAll("/","#slash#"));
			//interrogation du backup
			if(useBackup && file.exists() && file.lastModified()>=touched.getTime()) {
					//FileInputStream fis = null;
					BufferedReader in = null;
					try {
						
						StringBuffer b = new StringBuffer();
						/*
						int c;
						while ((c = fis.read()) != -1) {
							b.append((char) c);
						}
						content = b.toString();
						*/
						
						in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
						String str;
						while ((str = in.readLine()) != null) {
						    b.append(str+"\n");
						}
						content = b.toString();
						
						//System.out.println("["+title+"]loaded from backup");
					} catch (Exception e) {
						System.err.println(e.getMessage());
						e.printStackTrace();
					} finally {
						/*
						try {fis.close();} catch (Exception e) {}
						fis = null;
						 */						
						try {in.close();} catch (Exception e) {}
						in = null;
					}
			} else {
				// recuperation depuis le wiki
				Hashtable<String, String> parameters = new Hashtable<String, String>();
				parameters.put("action", "query");
				parameters.put("format", "xml");
				parameters.put("prop", "revisions");
				parameters.put("rvlimit", "1");
				parameters.put("rvprop", "content");
				parameters.put("titles", title);
				parameters.put("continue", "");
		
				Document document = API.post(parameters, m_from);
				Element root = (Element) document.getElementsByTagName("api").item(0);
				NodeList nodeList = root.getElementsByTagName("error");
				if(nodeList.getLength()>0) {
					Element error = (Element) nodeList.item(0);
					System.err.println("["+error.getAttribute("code")+"] : "+error.getAttribute("code"));
				} else {
					nodeList = root.getElementsByTagName("query");
					if(nodeList.getLength()>0) {
						nodeList = ((Element)nodeList.item(0)).getElementsByTagName("pages");
						if(nodeList.getLength()>0) {
							nodeList = ((Element)nodeList.item(0)).getElementsByTagName("page");
							if(nodeList.getLength()>0) {
								nodeList = ((Element)nodeList.item(0)).getElementsByTagName("revisions");
								if(nodeList.getLength()>0) {
									nodeList = ((Element)nodeList.item(0)).getElementsByTagName("rev");
									if(nodeList.getLength()>0) {
										content = nodeList.item(0).getTextContent();
										//System.out.println("["+title+"] loaded from wiki");
									} else {
										System.err.println("missing rev node in the response");
									}
								} else {
									System.err.println("missing revisions node in the response");
								}
							} else {
								System.err.println("missing page node in the response");
							}
						} else {
							System.err.println("missing pages node in the response");
						}
					} else {
						System.err.println("missing query node in the response");
					}
				}
				// copie vers le backup
//				if(content!=null) {
//					FileOutputStream fileOS = null;
//					try {
//						fileOS = new FileOutputStream(file);
//						fileOS.write(content.getBytes("UTF-8"));
//					} catch (Exception e) {
//						System.err.println(e.getMessage());
//						e.printStackTrace();
//					} finally {
//						try {fileOS.close();} catch (Exception e) {}
//						fileOS = null;
//					}
//				}
			}
		}
		return content;
	}
	
	/**
	 * Remplit l'article avec un nouveau contenu, et met à jour le cache
	 * @param content le contenu de l'article
	 * @param summary le résumé de la modification
     * @param isMinor si la modification est mineure ou non
	 * @return true en cas de réussite et false sinon
	 */
	public boolean setContent(String content, String summary, boolean isMinor) {
		boolean success = false;
		if(content!=null) {
			if(API.send(title, content, summary, isMinor)) {
				success = true;
				File file = new File(API.getBackupPath()+title.replaceAll("/","#slash#"));
//				FileOutputStream fileOS = null;
//				try {
//					fileOS = new FileOutputStream(file);
//					fileOS.write(content.getBytes("UTF-8"));
//					success = true;
//				} catch (Exception e) {
//					System.err.println(e.getMessage());
//					e.printStackTrace();
//				} finally {
//					try {fileOS.close();} catch (Exception e) {}
//					fileOS = null;
//				}
			}
		}
		return success;
	}

    public boolean setContent(String content, String summary) {
        return setContent(content, summary, false);
    }

    /**
     * Checks whether the page already exists on the wiki or not.
     * @return true if a page with this name exists on the wiki, false otherwise
     */
	public boolean doesPageExists() {
		Hashtable<String, String> parameters = new Hashtable<String, String>();
		parameters.put("action", "query");
		parameters.put("titles", title);
		parameters.put("format", "xml");

		Document document = API.post(parameters, m_from);
		Element root = (Element) document.getElementsByTagName("api").item(0);
		NodeList nodeList = root.getElementsByTagName("query");
		if(nodeList.getLength()>0) {
			nodeList = ((Element) nodeList.item(0)).getElementsByTagName("pages");
			if (nodeList.getLength() > 0) {
				nodeList = ((Element) nodeList.item(0)).getElementsByTagName("page");
				if (nodeList.getLength() > 0) {
					Element pageElement = (Element) nodeList.item(0);
					String idx = pageElement.getAttribute("_idx");

					return !idx.equals("-1");
				} else {
					System.err.println("error : page");
				}
			} else {
				System.err.println("error : pages");
			}
		} else {
			System.err.println("error : query");
		}

		throw new RuntimeException("Error in API response for page existence");
	}

    public boolean isRedirect() {
        if (content == null) {
            getContent();
        }

        return content.startsWith("#REDIRECT");
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Page page)) return false;
        return Objects.equals(title, page.title) && Objects.equals(content, page.content) && m_from == page.m_from;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, m_from);
    }
}
