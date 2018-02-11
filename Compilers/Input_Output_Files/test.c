#include <stdio.h>
int main(){
	int T_8, T_9, y, x, z, T_10, T_11, T_12;
	L_0:
	L_400: T_8 = x + y;
	L_410: T_9 = y + z;
	L_420: if (T_8 != T_9) goto L_440;
	L_430: goto L_490;
	L_440: goto L_480;
	L_450: T_10 = x * 2;
	L_460: if (T_10 >= 0) goto L_440;
	L_470: goto L_480;
	L_480: goto L_490;
	L_490: if (1 != 1) goto L_510;
	L_500: goto L_540;
	L_510: T_11 = x + 10;
	L_520: printf("%d", T_11);
	L_530: goto L_490;
	L_540: T_12 = 0;
	L_550: if (x != 1) goto L_590;
	L_560: T_12 = 1;
	L_570: printf("%d", 1);
	L_580: goto L_680;
	L_590: if (x != 2) goto L_630;
	L_600: T_12 = 1;
	L_610: printf("%d", 2);
	L_620: goto L_680;
	L_630: if (x != 3) goto L_670;
	L_640: T_12 = 1;
	L_650: printf("%d", 3);
	L_660: goto L_680;
	L_670: printf("%d", 0);
	L_680: {}
}