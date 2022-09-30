package com.cintap.transport.iftman.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Pieces")
public class Pieces {
	private List<Piece> Piece;

	@XmlElement(name = "Piece")
	public List<Piece> getPiece() {
		return Piece;
	}

	public void setPiece(List<Piece> piece) {
		Piece = piece;
	}

}
