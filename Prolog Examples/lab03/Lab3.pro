
%-----------------------------------------------------------------------------------------

%-- ASKHSH 1

p1(I,J) :- purchase(I,A1,C1,N1), purchase(J,A2,C2,N2), A1==A2.

p2(X,Y) :- item(C1,X,G1,P1), item(C2,Y,G2,P2), purchase(I1,A,C1,N1), purchase(I2,A,C2,N2).

p3(C) :- item(C,X,G1,P1), item(C2,X,G2,P2), P1/G1 > P2/G2.

p4(I,T) :- purchase(I,A,C,N), item(C,X,G,P), discount(A,D), T is (N*P)-(N*P)*D/100.

%-----------------------------------------------------------------------------------------
     
%-- ASKHSH 2

path(X,Y) :- link(X,Y).
path(X,Y) :- link(X,A), path(A,Y).

biconnected(X,Y) :- link(X,Y), link(X,A), path(A,Y).

meetpoint(S1,D1,S2,D2,X) :- source(S1), source(S2), destination(D1), destination(D2), path(S1,X), path(S2,X), path(X,D1), path(X,D2).

%-----------------------------------------------------------------------------------------
     
%-- ASKHSH 3

grade(A,B,C,E,G) :- A<1, G is 0.
grade(A,B,C,E,G) :- A>10, G is 0.
grade(A,B,C,E,G) :- B<1, G is 0.
grade(A,B,C,E,G) :- B>10, G is 0.
grade(A,B,C,E,G) :- C<1, G is 0.
grade(A,B,C,E,G) :- C>10, G is 0.
grade(A,B,C,E,G) :- E<1, G is 0.
grade(A,B,C,E,G) :- E>10,G is 0.
grade(A,B,C,E,G) :- M is 3/( 1/A + 1/B + 1/C ), M>E, A>=5, B>=5, C>=5, T is ((M+E)/2 + (M+E)/10), T>=10, G is 10.
grade(A,B,C,E,G) :- M is 3/( 1/A + 1/B + 1/C ), M>E, A>=5, B>=5, C>=5, T is ((M+E)/2 + (M+E)/10), T<10,G is T.
grade(A,B,C,E,G) :- M is 3/( 1/A + 1/B + 1/C ), M=<E, A>=5, B>=5, C>=5, T is (E + E*0.2), T>=10, G is 10.
grade(A,B,C,E,G) :- M is 3/( 1/A + 1/B + 1/C ), M=<E, A>=5, B>=5, C>=5, T is (E + E*0.2), G is T.
grade(A,B,C,E,G) :- M is 3/( 1/A + 1/B + 1/C ), M>E, G is ((M+E)/2).
grade(A,B,C,E,G) :- M is 3/( 1/A + 1/B + 1/C ), M=<E, G is (E) .

%-----------------------------------------------------------------------------------------
     
%-- ASKHSH 4

zeta(K,N,X,Y,G) :- Y>=K, N>=K, K>=1, !, O is Y-K, I is N-K, zeta(K, N, X, O, G1), zeta(K, I, X, G1, G).
zeta(K,N,X,Y,G) :- Y<K, N>=K+2, !, G is K.
zeta(K,N,X,Y,G) :- Y<K, N is K+1, !, G is N-2.
zeta(K,N,X,Y,G) :- Y<K, N==K, !, G is X.
zeta(K,N,X,Y,G) :- N<K, !, G is Y+K.
zeta(K,N,X,Y,G) :- K==0, !, G is Y+K.

%-----------------------------------------------------------------------------------------
     
%-- ASKHSH 5                                   

convert(0, 0).
convert(s(X), R) :- convert(X, P), R is P+1.

divide(X,0,undefined):- !.
divide(0,_,0):- !.
divide(X,s(Y),D) :- convert(X, R1), convert(s(Y), R2), L is div(R1,R2), convert(D, L).

%-----------------------------------------------------------------------------------------
%-----------------------------------------------------------------------------------------
%-----------------------------------------------------------------------------------------

%-- MHN TROPOPOIHSETE TO PARAKATW TMHMA KWDIKA 


item(n3001,coffee,100,1.25).
item(n3002,coffee,200,2.40).
item(n3205,sugar,500,1.80).
item(d1105,milk,500,0.65).
item(d1110,milk,1000,1.20).
item(k2105,bread,500,0.90).
item(k2110,bread,1000,1.80).
item(k2120,bread,2000,3.60).
item(z1005,water,500,0.35).
item(z1010,water,1000,0.80).
item(n3201,tea,100,1.48).

purchase(inv001,'Mickey Mouse',n3002,3).
purchase(inv002,'Mickey Mouse',n3205,1).
purchase(inv008,'Mickey Mouse',d1110,2).
purchase(inv004,'Donald Duck',d1105,15).
purchase(inv005,'Donald Duck',k2120,50).
purchase(inv003,'Lucky Luke',z1005,3).
purchase(inv006,'Cocco Bill',n3002,2).
purchase(inv007,'Lucky Luke',z1005,7).

discount('Mickey Mouse', 10).
discount('Donald Duck',25).
discount('Lucky Luke',35).
discount('Cocco Bill',0).
discount('Woody Woodpecker',5).

source(a).
source(b).
source(c).

destination(x).
destination(y).
destination(z).

link(a,d).
link(a,e).
link(b,d).
link(b,f).
link(c,f).
link(c,g).
link(c,q).
link(d,h).
link(d,i).
link(e,j).
link(f,k).
link(f,w).
link(g,l).
link(h,i).
link(i,p).
link(i,x).
link(j,x).
link(j,y).
link(j,z).
link(k,y).
link(l,m).
link(m,n).
link(n,o).
link(o,i).
link(p,y).
link(q,r).
link(r,s).
link(r,u).
link(s,t).
link(t,u).
link(u,x).
link(v,z).
link(w,v).
link(w,z).