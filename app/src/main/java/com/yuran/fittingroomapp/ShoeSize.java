package com.yuran.fittingroomapp;

/**
 * Created by Yuran on 15.12.2015.
 */
public class ShoeSize {

    public static  final int EU = 0;
    public static  final int US = 1;
    public static  final int UK = 2;
    //public static  final int RU = 3;
    public static  final int MALE = 0;
    public static  final int FEMALE = 1;


    public static double getSize(double length,int system, int sex )
    {
        double s_eu,s_us,s_uk,s_ru,s_eu_f,s_us_f,s_uk_f,s_ru_f;

        if(length<225.0) {
            s_eu  = 35;
            s_us  = 4;
            s_uk  = 3.5;
            s_ru  = 35;
            s_eu_f  = 35;
            s_us_f  = 5;
            s_uk_f  = 2.5;
            s_ru_f  = 35;
        }
        else if ((length>=225.0)&&(length<230.0)) {
            s_eu  = 36;
            s_us  = 4.5;
            s_uk  = 4;
            //s_ru  = 35;
            s_eu_f  = 35.5;
            s_us_f  = 5.5;
            s_uk_f  = 3;
            s_ru_f  = 35;
        }
        else if ((length>=230.0)&&(length<235.0)){
            s_eu  = 37;
            s_us  = 5.0;
            s_uk  = 4.5;
            s_ru  = 36;
            s_eu_f  = 35;
            s_us_f  = 6;
            s_uk_f  = 3.5;
            s_ru_f  = 35;
        }
        else if ((length>=235.0)&&(length<240.0)){
            s_eu  = 37.5;
            s_us  = 5.5;
            s_uk  = 5.0;
            s_ru  = 35;
            s_eu_f  = 37;
            s_us_f  = 6.5;
            s_uk_f  = 4;
            s_ru_f  = 35;
        }
        else if ((length>=240.0)&&(length<245.0)){
            s_eu  = 38;
            s_us  = 6.0;
            s_uk  = 5.5;
            s_ru  = 35;
            s_eu_f  = 37.5;
            s_us_f  = 7;
            s_uk_f  = 4.5;
            s_ru_f  = 35;
        }
        else if ((length>=245.0)&&(length<250.0)){
            s_eu  = 38.5;
            s_us  = 6.5;
            s_uk  = 6.0;
            s_ru  = 35;
            s_eu_f  = 38;
            s_us_f  = 7.5;
            s_uk_f  = 5;
            s_ru_f  = 35;
        }
        else if ((length>=250.0)&&(length<255.0)){
            s_eu  = 39;
            s_us  = 7.0;
            s_uk  = 6.5;
            s_ru  = 35;
            s_eu_f  = 38.5;
            s_us_f  = 8;
            s_uk_f  = 5.5;
            s_ru_f  = 35;
        }
        else if ((length>=255.0)&&(length<260.0)){
            s_eu  = 40;
            s_us  = 7.5;
            s_uk  = 7;
            s_ru  = 35;
            s_eu_f  = 39;
            s_us_f  = 8.5;
            s_uk_f  = 6;
            s_ru_f  = 35;
        }
        else if ((length>=260.0)&&(length<265.0)){
            s_eu  = 41;
            s_us  = 8;
            s_uk  = 7.5;
            s_ru  = 35;
            s_eu_f  = 40;
            s_us_f  = 9;
            s_uk_f  = 6.5;
            s_ru_f  = 35;
        }
        else if ((length>=265.0)&&(length<270.0)) {
            s_eu  = 42;
            s_us  = 8.5;
            s_uk  = 8;
            s_ru  = 35;
            s_eu_f  = 41;
            s_us_f  = 9.5;
            s_uk_f  = 7;
            s_ru_f  = 35;
        }
        else if ((length>=270.0)&&(length<275.0)){
            s_eu  = 43;
            s_us  = 9;
            s_uk  = 8.5;
            s_ru  = 35;
            s_eu_f  = 42;
            s_us_f  = 10;
            s_uk_f  = 7.5;
            s_ru_f  = 35;
        }
        else if ((length>=275.0)&&(length<280.0)){
            s_eu  = 44;
            s_us  = 10.5;
            s_uk  = 10;
            s_ru  = 35;
            s_eu_f  = 43;
            s_us_f  = 10.5;
            s_uk_f  = 8;
            s_ru_f  = 35;
        }
        else if ((length>=280.0)&&(length<285.0)){
            s_eu  = 45;
            s_us  = 11.5;
            s_uk  = 11;
            s_ru  = 35;
            s_eu_f  = 44;
            s_us_f  = 12;
            s_uk_f  = 9.5;
            s_ru_f  = 35;
        }
        else if ((length>=285.0)&&(length<290.0)){
            s_eu  = 46;
            s_us  = 12.5;
            s_uk  = 12;
            s_ru  = 35;
            s_eu_f  = 45;
            s_us_f  = 13;
            s_uk_f  = 10.5;
            s_ru_f  = 35;
        }else {
            s_eu  = 47;
            s_us  = 13.5;
            s_uk  = 13;
            s_ru  = 35;
            s_eu_f  = 46;
            s_us_f  = 14;
            s_uk_f  = 11;
            s_ru_f  = 35;
        }

        if(sex == MALE) {
            switch (system) {
                case EU:
                    return s_eu;
                case US:
                    return s_us;
                case UK:
                    return s_uk;
                default:
                    return s_eu;
            }
        }else
        {
            switch (system) {
                case EU:
                    return s_eu_f;
                case US:
                    return s_us_f;
                case UK:
                    return s_uk_f;
                default:
                    return s_eu_f;
            }
        }
    }

    public static double getSize2(double length,int system, int sex )
    {
        double s_eu,s_us,s_uk,s_ru,s_eu_f,s_us_f,s_uk_f,s_ru_f;

        if(length<225.0) {
            s_eu  = 35;
            s_us  = 4;
            s_uk  = 3.5;
            s_ru  = 35;
            s_eu_f  = 35;
            s_us_f  = 5;
            s_uk_f  = 2.5;
            s_ru_f  = 35;
        }
        else if ((length>=225.0)&&(length<230.0)) {
            s_eu  = 36;
            s_us  = 4.5;
            s_uk  = 4;
            //s_ru  = 35;
            s_eu_f  = 35.5;
            s_us_f  = 5.5;
            s_uk_f  = 3;
            s_ru_f  = 35;
        }
        else if ((length>=230.0)&&(length<235.0)){
            s_eu  = 37;
            s_us  = 5.0;
            s_uk  = 4.5;
            s_ru  = 36;
            s_eu_f  = 35;
            s_us_f  = 6;
            s_uk_f  = 3.5;
            s_ru_f  = 35;
        }
        else if ((length>=235.0)&&(length<240.0)){
            s_eu  = 37.5;
            s_us  = 5.5;
            s_uk  = 5.0;
            s_ru  = 35;
            s_eu_f  = 37;
            s_us_f  = 6.5;
            s_uk_f  = 4;
            s_ru_f  = 35;
        }
        else if ((length>=240.0)&&(length<245.0)){
            s_eu  = 38;
            s_us  = 6.0;
            s_uk  = 5.5;
            s_ru  = 35;
            s_eu_f  = 37.5;
            s_us_f  = 7;
            s_uk_f  = 4.5;
            s_ru_f  = 35;
        }
        else if ((length>=245.0)&&(length<250.0)){
            s_eu  = 38.5;
            s_us  = 6.5;
            s_uk  = 6.0;
            s_ru  = 35;
            s_eu_f  = 38;
            s_us_f  = 7.5;
            s_uk_f  = 5;
            s_ru_f  = 35;
        }
        else if ((length>=250.0)&&(length<255.0)){
            s_eu  = 40;
            s_us  = 7.0;
            s_uk  = 6.5;
            s_ru  = 35;
            s_eu_f  = 39.5;
            s_us_f  = 8.5;
            s_uk_f  = 5.5;
            s_ru_f  = 35;
        }
        else if ((length>=255.0)&&(length<260.0)){
            s_eu  = 40.5;
            s_us  = 7.5;
            s_uk  = 7;
            s_ru  = 35;
            s_eu_f  = 40;
            s_us_f  = 9;
            s_uk_f  = 6;
            s_ru_f  = 35;
        }
        else if ((length>=260.0)&&(length<265.0)){
            s_eu  = 41;
            s_us  = 8;
            s_uk  = 7.5;
            s_ru  = 35;
            s_eu_f  = 40.5;
            s_us_f  = 9.5;
            s_uk_f  = 6.5;
            s_ru_f  = 35;
        }
        else if ((length>=265.0)&&(length<270.0)) {
            s_eu  = 41.5;
            s_us  = 8.5;
            s_uk  = 8;
            s_ru  = 35;
            s_eu_f  = 41;
            s_us_f  = 10;
            s_uk_f  = 7;
            s_ru_f  = 35;
        }
        else if ((length>=270.0)&&(length<275.0)){
            s_eu  = 42;
            s_us  = 9;
            s_uk  = 8.5;
            s_ru  = 35;
            s_eu_f  = 42;
            s_us_f  = 10;
            s_uk_f  = 7.5;
            s_ru_f  = 35;
        }
        else if ((length>=275.0)&&(length<280.0)){
            s_eu  = 42.5;
            s_us  = 9.5;
            s_uk  = 10;
            s_ru  = 35;
            s_eu_f  = 43;
            s_us_f  = 10.5;
            s_uk_f  = 8;
            s_ru_f  = 35;
        }
        else if ((length>=280.0)&&(length<285.0)){
            s_eu  = 43;
            s_us  = 10;
            s_uk  = 11;
            s_ru  = 35;
            s_eu_f  = 44;
            s_us_f  = 12;
            s_uk_f  = 9.5;
            s_ru_f  = 35;
        }
        else if ((length>=285.0)&&(length<290.0)){
            s_eu  = 43.5;
            s_us  = 10.5;
            s_uk  = 12;
            s_ru  = 35;
            s_eu_f  = 45;
            s_us_f  = 13;
            s_uk_f  = 10.5;
            s_ru_f  = 35;
        }else if ((length>=290.0)&&(length<295.0)) {
            s_eu = 44;
            s_us = 11;
            s_uk = 12;
            s_ru = 35;
            s_eu_f = 45;
            s_us_f = 13;
            s_uk_f = 10.5;
            s_ru_f = 35;
        }else if ((length>=295.0)&&(length<300.0)) {
            s_eu = 44.5;
            s_us = 11.5;
            s_uk = 12;
            s_ru = 35;
            s_eu_f = 45;
            s_us_f = 13;
            s_uk_f = 10.5;
            s_ru_f = 35;
        }else {
            s_eu = 45.0;
            s_us = 12.0;
            s_uk = 12;
            s_ru = 35;
            s_eu_f = 45;
            s_us_f = 13;
            s_uk_f = 10.5;
            s_ru_f = 35;
        }

        if(sex == MALE) {
            switch (system) {
                case EU:
                    return s_eu;
                case US:
                    return s_us;
                case UK:
                    return s_uk;
                default:
                    return s_eu;
            }
        }else
        {
            switch (system) {
                case EU:
                    return s_eu_f;
                case US:
                    return s_us_f;
                case UK:
                    return s_uk_f;
                default:
                    return s_eu_f;
            }
        }
    }

    public static  String ToString(int type)
    {
        switch (type)
        {
            case EU:
                return "(EU)";
            case US:
                return "(US)";
            case UK:
                return "(UK)";
            default:
                return "(EU)";
        }
    }

    public static Boolean isValidLength(double size){
        if((size>200.0)&&(size<330.0))
            return true;
        else
            return false;
    }

    public static Boolean isValidWidth(double size){
        if((size>50.0)&&(size<200.0))
            return true;
        else
            return false;
    }
}
