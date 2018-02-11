function [] = trapeziumMethodProblem2(h)

%double precision
format long

%initialization of variables
y0 = [3; 0; 4];
A2 = [-3 -1 1; -1 -3 2; 1 2 -3];

%step 7
fprintf('\t nh \t ||y^n|| \t\t ||y^(n-1)||\n')
fprintf('--------------------------------------------------\n')

%initialization of variables as I say in theory
M1 = eye(3) + (h/2).*A2;
M2 = inv(eye(3) - (h/2).*A2);

t_ = [];
results = [];

%performing a method as I say in theory
for nh = 1:1:11 
   normaB = norm(y0);
   newV = M1*y0;
   newV = M2*newV;
   y0 = newV;
   norma = norm(newV);
   
   fprintf('\t %d        %d        %d \n', (nh - 1), norma, normaB)
   
   results = [results norma];
   t_ = [t_ (nh - 1)];
end

%plots
figure
plot(t_, results, 'r')
end