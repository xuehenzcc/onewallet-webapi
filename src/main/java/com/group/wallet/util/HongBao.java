package com.group.wallet.util;

import java.math.BigDecimal;
import java.util.Random;

public class HongBao {

    public static void main(String[] args) {
        //hb(100, 9, 0.01);//金额，个数，最少值
        //zb();
        hb2(5000, 10, 50);
    }


    public static void hb(double total,int num,double min){
        for(int i=1;i<num;i++){
            double safe_total=(total-(num-i)*min)/(num-i);
            double money=Math.random()*(safe_total-min)+min;
            BigDecimal money_bd=new BigDecimal(money);
            money=money_bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            total=total-money;
            BigDecimal total_bd=new BigDecimal(total);
            total=total_bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("第"+i+"个红包："+money+",余额为:"+total+"元");
        }
        System.out.println("第"+num+"个红包："+total+",余额为:0元");
    }

    void zb(){
        for(int a=0;a<=10000;a++){
            if(a % 1000== 0)
                System.out.println (a);
        }
    }


    public static void hb2(int total, int num, int max){
        for (int i = 0; i < num; i++) {
            if(i<num-1){
                int middle = new BigDecimal(total).divide(new BigDecimal(num-i), 0, BigDecimal.ROUND_HALF_UP).intValue();
                Random ran=new Random();
                int ranint = ran.nextInt(max);

                int money = middle-ranint;

                total = total-money;

                System.out.println("第"+(i+1)+"个红包:"+money+"，余额为"+total);
            }
            else {
                System.out.println("第"+(i+1)+"个红包:"+total+"，余额为"+total);
            }

        }

    }
}
