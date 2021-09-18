package composite;

import java.util.ArrayList;
import java.util.List;

class Test {
    public static void main(String[] args) {
        Print print = new Sentence(new Word('W', 'h', 'e', 'r', 'e'),
                new Word('t', 'h', 'e', 'r', 'e'),
                new Word('i', 's'),
                new Word('a'),
                new Word('w', 'h', 'i', 'p'),
                new Word('t', 'h', 'e', 'r', 'e'),
                new Word('i', 's'),
                new Word('a'),
                new Word('w', 'a', 'y'));
        //  Where there is a whip there is a way.
        print.print();
    }
}

// 部分与整体，句子，单词，字符，每个都能打印
abstract class PrintComposite implements Print {
    private final List<PrintComposite> childList = new ArrayList<>();

    public void add(PrintComposite composite) {
        childList.add(composite);
    }

    public void printBefore() {

    }

    public void printAfter() {

    }

    @Override
    public void print() {
        printBefore();
        printChildList();
        printAfter();
    }

    private void printChildList() {
        for (PrintComposite child : childList) child.print();
    }
}

interface Print {
    void print();
}

class Sentence extends PrintComposite {
    public Sentence(Word... words) {
        for (Word word : words) {
            add(word);
        }
    }

    @Override
    public void printAfter() {
        System.out.print(". ");
    }
}

class Word extends PrintComposite {

    public Word(char... chars) {
        for (char aChar : chars) {
            add(new Letter(aChar));
        }
    }

    @Override
    public void printAfter() {
        System.out.print(" ");
    }
}

class Letter extends PrintComposite {
    char value;

    public Letter(char value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}