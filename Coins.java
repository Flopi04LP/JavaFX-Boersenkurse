/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

}
