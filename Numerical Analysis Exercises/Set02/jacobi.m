function [  ] = jacobi( A,b )
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
        sum = 0;
        for i = 1:n
           sum = 0;
           for j = 1:n
              if i ~= j 
                 sum = sum + A(i,j)*X(j,1); 
              end
           end
           X(i,1) = (b(i,1) - sum) / A(i,i);
        end
        %Eykleidia norma
        Xxreal = X - xreal;
        sum2 = 0;
        for i = 1:n
           sum2 = sum2 + (Xxreal(i,1))*(Xxreal(i,1)); 
        end
        sum2 = sqrt(sum2);
        %Emfanizetai to sfalma pou theloume se kathe epanalipsi
        sum2
        if sum2 < ((1/10)*(1/10)*(1/10)*(1/10)) 
            r = 'To sfalma einai mikrotero apo to 10^(-4)'
            break;
        end
    end
    X
end