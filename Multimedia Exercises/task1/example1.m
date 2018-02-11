function [  ] = example1( )
X = [1 2 3 4 5 6 ;7 8 9 10 11 12;13 14 15 16 17 18; 19 20 21 22 23 24; 25 26 27 28 29 30; 31 32 33 34 35 36]
[M,N] = size(X);
k1 = M/3; 
k2 = N/3; 

% for x = 1:3:6
%     for y = 1:3:6
%             fprintf('x = %d, x + 2 = %d\n', x, x+2);
%             fprintf('y = %d, y + 2 = %d\n', y, y+2);
%             X(x:x+2, y:y+2)
%         end
%     end

for i=0:k1-1
    for j=0:k2-1
        i1 = 3*i + 1;
        i2 = 3*j + 1;
        X(i1:i1+2, i2:i2+2)
    end
end

end