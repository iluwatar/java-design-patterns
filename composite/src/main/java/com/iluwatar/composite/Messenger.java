package com.iluwatar.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Messenger
 *
 */
public class Messenger {

	LetterComposite messageFromOrcs() {

		List<Word> words = new ArrayList<Word>();

		words.add(new Word(Arrays.asList(new Letter('W'), new Letter('h'),
				new Letter('e'), new Letter('r'), new Letter('e'))));
		words.add(new Word(Arrays.asList(new Letter('t'), new Letter('h'),
				new Letter('e'), new Letter('r'), new Letter('e'))));
		words.add(new Word(Arrays.asList(new Letter('i'), new Letter('s'))));
		words.add(new Word(Arrays.asList(new Letter('a'))));
		words.add(new Word(Arrays.asList(new Letter('w'), new Letter('h'),
				new Letter('i'), new Letter('p'))));
		words.add(new Word(Arrays.asList(new Letter('t'), new Letter('h'),
				new Letter('e'), new Letter('r'), new Letter('e'))));
		words.add(new Word(Arrays.asList(new Letter('i'), new Letter('s'))));
		words.add(new Word(Arrays.asList(new Letter('a'))));
		words.add(new Word(Arrays.asList(new Letter('w'), new Letter('a'),
				new Letter('y'))));

		return new Sentence(words);

	}

	LetterComposite messageFromElves() {

		List<Word> words = new ArrayList<Word>();

		words.add(new Word(Arrays.asList(new Letter('M'), new Letter('u'),
				new Letter('c'), new Letter('h'))));
		words.add(new Word(Arrays.asList(new Letter('w'), new Letter('i'),
				new Letter('n'), new Letter('d'))));
		words.add(new Word(Arrays.asList(new Letter('p'), new Letter('o'),
				new Letter('u'), new Letter('r'), new Letter('s'))));
		words.add(new Word(Arrays.asList(new Letter('f'), new Letter('r'),
				new Letter('o'), new Letter('m'))));
		words.add(new Word(Arrays.asList(new Letter('y'), new Letter('o'),
				new Letter('u'), new Letter('r'))));
		words.add(new Word(Arrays.asList(new Letter('m'), new Letter('o'),
				new Letter('u'), new Letter('t'), new Letter('h'))));

		return new Sentence(words);

	}

}
