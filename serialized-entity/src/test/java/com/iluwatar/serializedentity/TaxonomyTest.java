package com.iluwatar.serializedentity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaxonomyTest {
    @Test
    void testGetTaxonomy() {
        Taxonomy human = new Taxonomy (
                5,
                "Eukarya",
                "Animalia",
                "Chordata",
                "Mammalia",
                "Primates",
                "Hominidae",
                "Homo",
                "H. sapiens");

        assertEquals(5, human.getSpeciesId());
        assertEquals("Eukarya", human.getDomain());
        assertEquals("Animalia",human.getKingdom());
        assertEquals("Chordata", human.getPhylum());
        assertEquals("Mammalia",human.getClassis());
        assertEquals("Primates",human.getOrder());
        assertEquals("Hominidae",human.getFamily());
        assertEquals("Homo",human.getGenus());
        assertEquals("H. sapiens",human.getSpecies());
    }

    @Test
    void testSetTaxonomy() {
        Taxonomy fruitFly = new Taxonomy (
                2,
                "Eukarya",
                "Animalia",
                "Arthropoda",
                "Insecta",
                "Diptera",
                "Drosophilidae",
                "Drosophila",
                "D. melanogaster");

        fruitFly.setDomain("Eukarya");
        fruitFly.setKingdom("Animalia");
        fruitFly.setPhylum("Chordata");
        fruitFly.setClassis("Mammalia");
        fruitFly.setOrder("Primates");
        fruitFly.setFamily("Hominidae");
        fruitFly.setGenus("Homo");
        fruitFly.setSpecies("H. sapiens");

        assertEquals(2, fruitFly.getSpeciesId());
        assertEquals("Eukarya", fruitFly.getDomain());
        assertEquals("Animalia",fruitFly.getKingdom());
        assertEquals("Chordata", fruitFly.getPhylum());
        assertEquals("Mammalia",fruitFly.getClassis());
        assertEquals("Primates",fruitFly.getOrder());
        assertEquals("Hominidae",fruitFly.getFamily());
        assertEquals("Homo",fruitFly.getGenus());
        assertEquals("H. sapiens",fruitFly.getSpecies());
    }

    @Test
    void testEmptyTaxonomy() {
        Taxonomy emptyLivingThing = new Taxonomy (
                0,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "");;

        assertEquals(0, emptyLivingThing.getSpeciesId());
        assertEquals("", emptyLivingThing.getDomain());
        assertEquals("",emptyLivingThing.getKingdom());
        assertEquals("", emptyLivingThing.getPhylum());
        assertEquals("",emptyLivingThing.getClassis());
        assertEquals("",emptyLivingThing.getOrder());
        assertEquals("",emptyLivingThing.getFamily());
        assertEquals("",emptyLivingThing.getGenus());
        assertEquals("",emptyLivingThing.getSpecies());
    }

    @Test
    void testEqualsTaxonomy() {
        Taxonomy human1 = new Taxonomy (
                5,
                "Eukarya",
                "Animalia",
                "Chordata",
                "Mammalia",
                "Primates",
                "Hominidae",
                "Homo",
                "H. sapiens");

        Taxonomy human2 = new Taxonomy (
                2,
                "Eukarya",
                "Animalia",
                "Chordata",
                "Mammalia",
                "Primates",
                "Hominidae",
                "Homo",
                "H. sapiens");

        assertNotEquals(human1, human2);
        human2.setSpeciesId(5);
        assertEquals(human1, human2);
    }

}
