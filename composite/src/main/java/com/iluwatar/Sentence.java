package com.iluwatar;

import java.util.List;

public class Sentence extends LetterComposite {

	public Sentence(List<Word> words) {
		for (Word w: words) {
			this.add(w);
		}
	}
	
	@Override
	protected void printThisBefore() {
		// nop
	}

	@Override
	protected void printThisAfter() {
		System.out.print(".");
	}


}
