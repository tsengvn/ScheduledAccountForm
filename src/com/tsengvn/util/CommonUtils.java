package com.tsengvn.util;

public class CommonUtils {
	public static String moneyToWord(String number)
    {
        String[] strTachPhanSauDauPhay;
        if (number.contains(".") || number.contains(","))
        {
            strTachPhanSauDauPhay = number.split(",");
            return(moneyToWord(strTachPhanSauDauPhay[0]) + "phẩy " + moneyToWord(strTachPhanSauDauPhay[1]));
        }
        
        String[] dv = { "", "mươi", "trăm", "nghìn", "triệu", "tỉ" };
        String[] cs = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };
        String doc;
        int i, j, k, n, len, found, ddv, rd;

        len = number.length();
        number += "ss";
        doc = "";
        found = 0;
        ddv = 0;
        rd = 0;

        i = 0;
        while (i < len)
        {
            //So chu so o hang dang duyet
            n = (len - i + 2) % 3 + 1;

            //Kiem tra so 0
            found = 0;
            for (j = 0; j < n; j++)
            {
                if (number.charAt(i + j) != '0')
                {
                    found = 1;
                    break;
                }
            }

            //Duyet n chu so
            if (found == 1)
            {
                rd = 1;
                for (j = 0; j < n; j++)
                {
                    ddv = 1;
                    switch (number.charAt(i + j))
                    {
                        case '0':
                            if (n - j == 3) doc += cs[0] + " ";
                            if (n - j == 2)
                            {
                                if (number.charAt(i + j + 1) != '0') doc += "linh ";
                                ddv = 0;
                            }
                            break;
                        case '1':
                            if (n - j == 3) doc += cs[1] + " ";
                            if (n - j == 2)
                            {
                                doc += "mười ";
                                ddv = 0;
                            }
                            if (n - j == 1)
                            {
                                if (i + j == 0) k = 0;
                                else k = i + j - 1;

                                if (number.charAt(k) != '1' && number.charAt(k) != '0')
                                    doc += "mốt ";
                                else
                                    doc += cs[1] + " ";
                            }
                            break;
                        case '5':
                            if ((i + j == len - 1) ||(i + j + 3 == len -1))
                                doc += "lăm ";
                            else
                                doc += cs[5] + " ";
                            break;
                        default:
                            doc += cs[(int)number.charAt(i + j) - 48] + " ";
                            break;
                    }

                    //Doc don vi nho
                    if (ddv == 1)
                    {
                        doc += ((n - j) != 1) ? dv[n - j - 1] + " " : dv[n - j - 1];
                    }
                }
            }


            //Doc don vi lon
            if (len - i - n > 0)
            {
                if ((len - i - n) % 9 == 0)
                {
                    if (rd == 1)
                        for (k = 0; k < (len - i - n) / 9; k++)
                            doc += "tỉ ";
                    rd = 0;
                }
                else
                    if (found != 0) doc += dv[((len - i - n + 1) % 9) / 3 + 2] + " ";
            }

            i += n;
        }

        if (len == 1)
            if (number.charAt(0) == '0' || number.charAt(0) == '5') return cs[(int)number.charAt(0) - 48];
        
//        doc += "đồng";
        return doc;
    }
//	
//	public static void main (String[] args){
//		System.out.println(moneyToWord("1234000000"));
//	}
}
