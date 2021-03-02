package ch.floundsimon.ch.boerse;

/**
 *
 * @author kappe
 */
public enum Coins {
    BITCOIN,
    ETHEREUM,
    DOGECOIN;

    public String toString(Coins a) {
        return String.valueOf(a).toLowerCase();
    }
    public static String cleanString(Coins a){
        char[] array = String.valueOf(a).toCharArray();
        String first = String.valueOf(array[0]).toUpperCase();
        String rest ="";
        for(int i = 1; i<array.length; i++){
        rest += String.valueOf(array[i]).toLowerCase();
        }
        return first+rest;
    }

}
