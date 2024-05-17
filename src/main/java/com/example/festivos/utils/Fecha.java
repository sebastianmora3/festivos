package com.example.festivos.utils;

import java.util.HashMap;
import java.util.Map;

public class Fecha {

    public static boolean validarFecha(int anio, int mes, int dia) {

        if (anio < 1 || mes < 1 || mes > 12 || dia < 1) {
            return false;
        }

        int[] diasPorMes = obtenerDiasPorMes(anio);
        return dia <= diasPorMes[mes - 1];
    }

    private static int[] obtenerDiasPorMes(int anio) {
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (anioBisiesto(anio)) {
            diasPorMes[1] = 29;
        }
        return diasPorMes;
    }

    private static boolean anioBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0;
    }

    public static int zeller(int anio, int mes, int dia){

        if(mes <= 2){
            mes += 12;
            anio -= 1;
        }

        int J = anio/100;
        int K = anio % 100;

        return  (dia + ( 13*(mes + 1)/5 ) + K + (K/4) + (J/4) + 5*J ) % 7;
    }


    public  static int[] sumarDias(int anio, int mes, int dia, int diasSumar){

        int[] diasPorMes = obtenerDiasPorMes(anio);
        dia+= diasSumar;

        while (dia < 1) {
            mes--;
            dia += diasPorMes[mes - 1];
        }

        while (dia > diasPorMes[mes - 1]){
            dia -= diasPorMes[mes - 1];
            mes++;
        }

        return new int[]{dia, mes};
    }

    public static int[] diaPascua(int anio){

        int a =  anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (19*a + 24) % 30;

        int dias = d + (2*b + 4*c + 6*d + 5) % 7;

        return sumarDias(anio, 3, 15, dias+7);
    }

    public static int[] puenteFestivo(int anio, int mes, int dia){

        int n = zeller(anio, mes, dia);
        System.out.printf("\n Valor de n inicial "+ n);

        Map<Integer, Integer> valoresASumar = new HashMap<>();
        valoresASumar.put(1, 1);
        valoresASumar.put(0, 2);
        valoresASumar.put(6, 3);
        valoresASumar.put(5, 4);
        valoresASumar.put(4, 5);
        valoresASumar.put(3, 6);

        n = valoresASumar.getOrDefault(n, 0);
        System.out.printf("\n Valor de n Nuevo "+ n);


        return sumarDias(anio, mes, dia, n);
    }
}