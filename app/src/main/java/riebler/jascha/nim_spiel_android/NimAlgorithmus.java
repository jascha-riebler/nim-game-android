package riebler.jascha.nim_spiel_android;

import java.util.Arrays;
import java.util.Random;

public class NimAlgorithmus {
    public static int[] getSpielzug(int[] spielfeld, boolean misere, int level) {
        Random rn = new Random();
        int[] spielbrett = spielfeld.clone();
        if (rn.nextInt(9)+1 < level) {
            if (Arrays.equals(spielbrett,getOptimalerSpielzug(spielfeld,misere))) {
                getSchadensbegrenzenderSpielzug(spielfeld);
            }
        } else {
            getZufaelligerSpielzug(spielfeld);
        }
        return spielfeld;
    }
    //
    public static int[] getZufaelligerSpielzug(int[] spielfeld){
        Random rn = new Random();
        while (true) {
            int reihe = rn.nextInt(spielfeld.length);
            if (spielfeld[reihe]!=0) {
                spielfeld[reihe] = spielfeld[reihe] - rn.nextInt(spielfeld[reihe]) - 1;
                return spielfeld;
            }
        }
    }
    //
    public static int[] getSchadensbegrenzenderSpielzug(int[] spielfeld){
        Random rn = new Random();
        while (true) {
            int reihe = rn.nextInt(spielfeld.length);
            if (spielfeld[reihe]!=0) {
                spielfeld[reihe] = spielfeld[reihe] - 1;
                return spielfeld;
            }
        }
    }
    //
    public static int[] getOptimalerSpielzug(int[] spielfeld, boolean misere) {
        String nimsumme = "";
        int ReihenUeberEins = 0;
        int ReiheUeberEins = 0;
        int ReihenMitEins = 0;
        int ReiheMitEins = 0;
        for (int i = spielfeld.length-1; i>=0; i--) {
            nimsumme = addierenBinaer(nimsumme, umrechnenBinaer(spielfeld[i]));
            if (spielfeld[i]>1) {
                ReihenUeberEins++;
                ReiheUeberEins = i;
            } else if (spielfeld[i]==1){
                ReihenMitEins++;
                ReiheMitEins = i;
            }
        }
        if (misere && ReihenUeberEins<=1) {
            if (ReihenUeberEins==1 && ReihenMitEins%2==1) {
                spielfeld[ReiheUeberEins] = 0;
            } else if (ReihenUeberEins==1 && ReihenMitEins%2==0) {
                spielfeld[ReiheUeberEins] = 1;
            } else if (ReihenMitEins%2==0) {
                spielfeld[ReiheMitEins] = 0;
            } // end of if-else
            return spielfeld;
        }
        for (int reihe = 0; reihe < spielfeld.length ; reihe++) {
            int aenderung = spielfeld[reihe] - umrechnenDezimal(addierenBinaer(nimsumme, umrechnenBinaer(spielfeld[reihe])));
            if (aenderung > 0) {
                spielfeld[reihe] = spielfeld[reihe] - aenderung;
                break;
            }
        }
        return spielfeld;
    }
    //
    private static String umrechnenBinaer(int dezimal) {
        String binaer = "";
        while (dezimal > 0){
            binaer = dezimal % 2 + binaer;
            dezimal = dezimal / 2;
        }
        return binaer;
    }
    //
    private static int umrechnenDezimal(String binaer) {
        int dezimal = 0;
        int zaeler = 1;
        for (int i = binaer.length()-1; i>=0; i--) {
            if ((""+binaer.charAt(i)).equals("1")) {
                dezimal = dezimal + zaeler;
            }
            zaeler = zaeler*2;
        }
        return dezimal;
    }
    //Module 2 Addition (ohne Übertragung)
    private static String addierenBinaer(String summand1, String summand2) {
        int laengenunterschied = summand1.length() - summand2.length();
        if (laengenunterschied > 0) {
            for (int i = laengenunterschied; i>0; i--) {
                summand2 = "0" + summand2;
            }
        } else if (laengenunterschied < 0){
            for (int i = laengenunterschied*(-1); i>0; i--) {
                summand1 = "0" + summand1;
            }
        }
        String summe = "";
        for (int i = summand1.length()-1; i>=0; i--) {
            if (summand1.charAt(i) == summand2.charAt(i)) {
                summe = "0" + summe;
            } else {
                summe = "1" + summe;
            }
        }
        return summe;
    }
}
