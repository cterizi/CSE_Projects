function [ x ] = solveSystems(L, b, U, n)
    %y = inv(L)*b;
    %Tha vroume to dianysma y xrhsimopoiwntas thn pros ta empros antikatastasi
    for j=1:n
       sum = 0;
       for w = 1:j-1
        sum = sum + L(j,w)*y(w,1);
       end
       y(j,1) = (b(j,1) - sum)/L(j,j);  
    end
    
    %x = inv(U)*y;
    %Tha ypologisoume to diamysmato x xrhsimopoiwntas thn pros at pisw antikatastasi
    x(1:n) = 0;
    x = x';
    for j = n:-1:1
       sum = 0;
       for w = n:-1:j
        sum = sum + U(j,w)*x(w,1);
       end
       x(j,1) = (y(j,1) - sum)/U(j,j);
    end
end