%-----------------------------------------------------------------------------------------
%-- ASKHSH 1

fracSum(A1/B1,A2/B2,Z1/Z2) :- R2 is (B1*B2), R1 is (A1*B2 + A2*B1), gcd(R1,R2,R), Z2 is div(R2,R), Z1 is div(R1,R) .
%-- Gcd
gcd(N,N,N).
gcd(M,N,D) :- M<N, K is (N-M), gcd(M,K,D).
gcd(M,N,D) :- M>N, K is (M-N), gcd(N,K,D).  

%-----------------------------------------------------------------------------------------
%-- ASKHSH 2

sumd0(N1,1,1) :- !.
sumd0(N1,I1,S1) :- R1 is mod(N1,I1), R1 == 0, !, I3 is (I1-1), sumd0(N1,I3,S), S1 is (I1 + S).
sumd0(N1,I1,S1) :- R1 is mod(N1,I1), I3 is (I1-1), sumd0(N1,I3,S), S1 is S.
sumd(N,S) :- N>0, I is N, sumd0(N,I,S).

%-----------------------------------------------------------------------------------------
%-- ASKHSH 3

alpha(L,S) :- myAlpha(L,S,1).

okay(A,B,C) :- B>A, B>=C.
okay(A,B,C) :- B<A, B=<C.

myAlpha([X],[X],1) :- !.
myAlpha([X],[X],0) :- !.
myAlpha([],[],1) :- !.
myAlpha([],[],0) :- !.
myAlpha([H|T],[H|S],1) :- myAlpha([H|T],S,0).
myAlpha([H1,H2,H3|T],[H2|S],0)   :- okay(H1,H2,H3), myAlpha([H2,H3|T],S,0), !.
myAlpha([H1,H2,H3|T],S,0) :- \+ okay(H1,H2,H3), myAlpha([H2,H3|T],S,0), !.
myAlpha([H|[]],[H|S],0) :- myAlpha(T, S,0), !.
myAlpha([H|T], S,0) :- myAlpha(T, S,0).

%-----------------------------------------------------------------------------------------
%-- ASKHSH 4

expand(L,E) :- lengthList(L,N), myExpand(N,0,EX), K is (EX-N), nil(K,LISTA), conc(L,LISTA,E).

%-- Concatenate two lists
conc(S,[],S).
conc([],L,L).
conc([H1|T1],L,[H1|Q]) :- conc(T1,L,Q).

%-- List of nil
nil(N, [nil|Tail]) :- N > 0, N2 is N-1, nil(N2, Tail).
nil(0, []).

%-- Expand with parameter the length oh the list
myExpand(0,0,1).
myExpand(1,0,1).
myExpand(2,0,2).
myExpand(X,Y,Z) :- X is (2^Y), Z is X.
myExpand(X,Y,Z) :- (2^Y) < X, K is (Y+1), myExpand(X,K,Z).
myExpand(X,Y,Z) :- (2^Y) > X, Z is (2^Y). 

%-- Length list
lengthList([],0).
lengthList([H|T],N) :- lengthList(T,M), N is M+1.

%-----------------------------------------------------------------------------------------
%-- ASKHSH 5                                   

puzzle(L,N) :- contain(L,N).
puzzle(L,N) :- combination(L,A,B), remove(L,A,F1), remove(F1,B,F2), sum(A,B,Z), append(F2,[Z],F3), puzzle(F3,N).
puzzle(L,N) :- combination(L,A,B), remove(L,A,F1), remove(F1,B,F2), sub(A,B,Z), append(F2,[Z],F3), puzzle(F3,N).
puzzle(L,N) :- combination(L,A,B), remove(L,A,F1), remove(F1,B,F2), divide(A,B,Z), append(F2,[Z],F3), puzzle(F3,N).
puzzle(L,N) :- combination(L,A,B), remove(L,A,F1), remove(F1,B,F2), mul(A,B,Z), append(F2,[Z],F3), puzzle(F3,N).

%-- List contains the element N
contain([N|T],N).
contain([H|T],N) :- contain(T,N).

%-- All combination
combination(L,A,B) :- contain(L,A), remove(L,A,F), contain(F,B).

%-- Remove
remove([],_,[]). 
remove([N|T],N,T) :- !.
remove([H|T],N,[H|F]) :- remove(T,N,F).

%-- Sum
sum(X,Y,Z) :- Z is (X+Y).

%-- Subtraction
sub(X,Y,Z) :- X>Y, Z is (X-Y).

%-- Divide
divide(X,Y,Z) :- 0 is mod(X,Y), Z is div(X,Y).

%-- Μultiplication
mul(X,Y,Z) :- Z is (X*Y).