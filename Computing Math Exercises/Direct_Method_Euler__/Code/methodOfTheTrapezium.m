function [] = methodOfTheTrapezium(N)

%?????? ????????
format long

%???????? ???? ???????????
boundA = 0;
%????? ???? ???????????
boundB = 10;
%????
h = 10/N;

%??????? ??? ????? t ?? ???? ??? ????? ??? t ??? ??? ???????? ????? ??? ?
%??? ??????? ??? ?????? ???? ?????????
t = [];
for n = 0:1:N
    step = boundA + h*n;
    t = [t step]; 
end

%???? ??? ????? t ????????
t = t';
[sizeTx, sizeTy] = size(t);

%?????????? ??? ????? ??? ??? ???????????? ???? ???? 0 ???? ??? ???
%????????? x ??? ??? ??? ??? y
Xt_ = [1];
Yt_ = [1];

%?????? ??? ?????? ??? ?????????
M1 = inv(eye(2) - ((h/2).*[-11 100; 1 -11]));
M2 = eye(2) + (h/2).*[-11 100; 1 -11];
for i = 2:1:sizeTx 
    newV = M2*[Xt_(1, i-1); Yt_(1, i-1)];
    newV = M1*newV;
    Xt_ = [Xt_ newV(1,1)];
    Yt_ = [Yt_ newV(2,1)];
end

%????????? ??? ?????? ?? ??????????
Xt_ = Xt_';
Yt_ = Yt_';

%?????? ??? ???????????? ??? ?? x ??? y ???? ???? t = 10
Xt_(21,1)
Yt_(21,1)

end