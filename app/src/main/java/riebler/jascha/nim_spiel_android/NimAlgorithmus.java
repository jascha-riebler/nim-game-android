package riebler.jascha.nim_spiel_android;

import java.util.Arrays;
import java.util.Random;

public class NimAlgorithmus {
    public static int[] getSpielzug(int[] spielfeld, boolean misere, int level) {
        //Die Methode getSpielzug wird innerhalb der App aufgerufen und ihr werden ein Array, das Auskunft über das aktuelle Spielfeld gibt, ein boolean, der zu erkennen gibt um welche Spielvariante es sich handelt (false -> letzter Zug gewinnt / true-> letzter Zug verliert) und das Schwierigkeitslevel übergeben.
        Random rn = new Random();
        int[] spielbrett = spielfeld.clone(); //Das Spielfeld Array wird kopiert um später einen Vergleich zu ermöglichen. Eine Zuweisung (b = a) jedoch nicht, da hierbei a und b auf das gleiche Array verweisen würden.
        //Im Folgenden wird "ausgelost" ob ein optimaler oder zufälliger Spielzug zurückgegeben wird. Mit höherem Level steigt auch die Wahrscheinlichkeit eines optimalen Spielzuges (Level 1: 0% -> Level 10: 100%)
        if (rn.nextInt(9)+1 < level) {
            if (Arrays.equals(spielbrett,getOptimalerSpielzug(spielfeld,misere))) {
                //Falls es keinen optimalen Spielzug gibt, wird ein "schadensbegrenzender" Spielzug zurückgegeben.
                getSchadensbegrenzenderSpielzug(spielfeld);
            }
        } else {
            getZufaelligerSpielzug(spielfeld);
        }
        return spielfeld;
    }
    public static int[] getZufaelligerSpielzug(int[] spielfeld){
        // Aus einer zufälligen Reihe, wird eine bestimmte Anzahl an Spielsteinen entfernt. Diese kann zwischen einem Sielstein und allen Sielsteinen der Reihe liegen.
        Random rn = new Random();
        while (true) {
            int reihe = rn.nextInt(spielfeld.length);
            if (spielfeld[reihe]!=0) {
                spielfeld[reihe] = spielfeld[reihe] - 1 - rn.nextInt(spielfeld[reihe]);
                return spielfeld;
            }
        }
    }
    public static int[] getSchadensbegrenzenderSpielzug(int[] spielfeld){
        //Der "schadensbegrenzende" Spielzug besteht darin, lediglich einen Spielstein aus einer zufälligen Reihe zu entvernen, um dem Spieler noch möglichst viel Platz für Fehler zu lassen.
        Random rn = new Random();
        while (true) {
            int reihe = rn.nextInt(spielfeld.length);
            if (spielfeld[reihe]!=0) {
                spielfeld[reihe] = spielfeld[reihe] - 1;
                return spielfeld;
            }
        }
    }
    public static int[] getOptimalerSpielzug(int[] spielfeld, boolean misere) {
        String nimsumme = "";
        int ReihenUeberEins = 0;
        int ReiheUeberEins = 0;
        int ReihenMitEins = 0;
        int ReiheMitEins = 0;
        for (int i = spielfeld.length-1; i>=0; i--) {
            //Es wird die "Nimsumme" (binäre Addition ohne Übertragung aller Reihen) gebildet um zu sehen ob es sich um ein "ausgeglichenes" Spielfeld (Nimsumme = 0) handelt und/oder um den Spielzug ersichtlich zu machen, der das Spielfeld wieder "ausgleicht".
            nimsumme = addierenBinaer(nimsumme, umrechnenBinaer(spielfeld[i]));
            //Im Folgenden werden die Reihen mit mehr als einem Spielstein und die Reihen mit genau einem Spielstein gezählt. Es wird jeweils eine entsprechende Reihe gespiechert.
            if (spielfeld[i]>1) {
                ReihenUeberEins++;
                ReiheUeberEins = i;
            } else if (spielfeld[i]==1){
                ReihenMitEins++;
                ReiheMitEins = i;
            }
        }
        if (misere && ReihenUeberEins<=1) {
            //Falls es sich um die "Misere" Variante des Spiels handeln sollte und es nur noch eine oder gar keine Reihe mehr gibt, in der sich mehr als ein Spielstein befinden, wird die "Taktik" geändert.
            if (ReihenUeberEins==1 && ReihenMitEins%2==1) {
                spielfeld[ReiheUeberEins] = 0; //Gibt es nur noch eine Reihe mit mehr als einem Spielstein und eine ungerade Anzahl an Reihen mit einem Spielstein, werden alle verbleibenden Spielsteine aus der "langen" Reihe entfernt.
            } else if (ReihenUeberEins==1 && ReihenMitEins%2==0) {
                spielfeld[ReiheUeberEins] = 1; //Gibt es nur noch eine Reihe mit mehr als einem Spielstein und eine gerade Anzahl an Reihen mit einem Spielstein, werden alle verbleibenden Spielsteine bis auf einen aus der "langen" Reihe entfernt.
            } else if (ReihenMitEins%2==0) {
                spielfeld[ReiheMitEins] = 0; //Treffen beide Fälle nicht zu (gibt es also keine Reihe mit mehr als einem Spielstein mehr) und gibt es eine gerade Anzahl an Reihen mit einem Spielstein, wird der letzte Spielstein aus einer dieser Reihen entfernt.
            }
            return spielfeld;
        }
        for (int reihe = 0; reihe < spielfeld.length; reihe++) {
            //Die einzelnen Reihen werden durchgegangen und jeweils auf die Änderung untersucht, die in dieser Reihe nötig wäre um das Spielfeld auszugleichen (Nimsumme = 0).
            int aenderung = spielfeld[reihe] - umrechnenDezimal(addierenBinaer(nimsumme, umrechnenBinaer(spielfeld[reihe]))); //Die in eine dezimale Zahl umgewandelte binäre Differenz (= Summe) ohne Übetragung von der Nimsumme und der sich in der Reihe bindenden Speilsteine, entspricht den Spielsteinen die sich in dieser Reihe für eine Nimsumme von 0 befinden müssten. Sie wird von der aktuellen Anzahl der Spielsteine dieser Reihe abgezogen um die Änderung zu erhalten.
            if (aenderung > 0) {
                //Falls ein Spielzug gefunden wurden, der das Spielfeld ausgleichen würde (der nicht darin besteht Spielsteine hinzuzufügen), wird dieser durchgeführt und die Schleife verlassen.
                spielfeld[reihe] = spielfeld[reihe] - aenderung;
                break;
            }
        }
        return spielfeld;
    }
    private static String umrechnenBinaer(int dezimal) {
        //Umwandlung von einer dezimalen Zahl (in Form eines Integers) in eine binäre Zahl (in Form eines Strings).
        String binaer = "";
        while (dezimal > 0){
            binaer = dezimal % 2 + binaer;
            dezimal = dezimal / 2;
        }
        return binaer;
    }
    //
    private static int umrechnenDezimal(String binaer) {
        //Umwandlung von einer binären Zahl (in Form eines Strings) in eine dezimale Zahl (in Form eines Integers).
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
    private static String addierenBinaer(String summand1, String summand2) {
        //Addition von binären Zahlen ohne Übertragung.
        int laengenunterschied = summand1.length() - summand2.length();
        //Falls die Strings sich in ihrer Länge unterscheiden werden entsprechend viele Nullen "vorne" an den kürzeren String angefügt.
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
