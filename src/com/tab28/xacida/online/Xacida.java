package com.tab28.xacida.online;

public class Xacida {
	String name;
	String auteur;
	String url;
	String urlAudio;

	@Override
	public String toString() {
		return "Xacida [name=" + name + ", auteur=" + auteur + ", url=" + url
				+ ", urlAudio=" + urlAudio + "]";
	}

	public Xacida() {
	}

	public Xacida(String name, String auteur, String url, String urlAudio) {
		super();
		this.name = name;
		this.auteur = auteur;
		this.url = url;
		this.urlAudio = urlAudio;
	}

	public Xacida(String name, String auteur, String url) {
		super();
		this.name = name;
		this.auteur = auteur;
		this.url = url;
	}

	public Xacida(String name, String auteur) {
		super();
		this.name = name;
		this.auteur = auteur;
	}

	public Xacida(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlAudio() {
		return urlAudio;
	}

	public void setUrlAudio(String urlAudio) {
		this.urlAudio = urlAudio;
	}

}
