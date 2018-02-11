function [  ] = gaussSeidel( A,b )
    [m,n] = size(A);
    
    %Elegxoume ama o pinakas einai tetragwnikos kai emfanizoume sxetiko
    %mhnyma
    if m ~= n
       r = 'Matrix A isn;t square'
       return;
    end
    
    %Arxiki proseggish tou dianysmatos X
    for i = 1:n
       X(i,1) = 0; 
    end
    
    %Akrivis lysh tou systhmatos A*x=b => x(real) = A(-1)*b
    xreal = inv(A)*b;
    
    %K einai o metritis gia to # twn epanalipsewn
    k = 20;
    
    for count = 1:k
        for i = 1:n
            X(i,1) = b(i,1);
            X(i,1) = X(i,1) - A(i,1:i-1) * X(1:i-1);
            X(i,1) = X(i) - A(i,i+1:n) * xreal(i+1:n);
            X(i,1) = X(i) / A(i,i);
        end
        
        %Eykleideia norma
        Xxreal = X - xreal;
        sfalma = 0;
        for i = 1:n
           sfalma = sfalma + (Xxreal(i,1))*(Xxreal(i,1)); 
        end
        sfalma = sqrt(sfalma);
        %Emfanizetai to sfalma pou theloume se kathe epanalipsi
        sfalma
        if sfalma < ((1/10)*(1/10)*(1/10)*(1/10)) 
            r = 'To sfalma einai mikrotero apo to 10^(-4)'
            break;
        end
    end
    X
end