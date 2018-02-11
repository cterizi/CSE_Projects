function [  ] = cholesky( x, y, z, n, a, r, c)
    
    %ftiaxnw ton pinaka A1, arxika ton gemizw me 0
    A1=zeros(n);
    
    %trid(x, y, z)
    %gemizoume thn kyria diagwnio me to x
    for i = 1:n
        A1(i,i) = x;
    end
    
    %gemizw ton pinaka me to y
    for i = 1:n-1
        A1(i,i+1) = y;
    end
    for i = 2:n
       A1(i,i-1) = y; 
    end
    
    %gemizw ton pinaka me to z
    for i = 1:n-2
        A1(i, i+2) = z;
    end
    for i = 3:n
        A1(i, i-2) = z;
    end
    A1 = single(A1);
    %telos gemismatos tou pinaka
    
    %ftiaxnw ton pinaka L, kai ton arkikopoiw sto 0
    L = zeros(n);
    
    %arxizw kai ylopoiw ton algorithmo tou cholesky
    for i = 1:n
        sum2 = 0;
        for j = 1:i-1
            sum1 = 0;
            %edw theloume na kanoume exoikonomish stin mnhmh 
            %gia auto anti gia k = 1 -> k = (i-2)
            if i-2 > 0
                for k = (i-2):j-1
                    sum1 = sum1 + L(i,k)*L(j,k);
                end
            end
            L(i, j) = (A1(i,j) - sum1)/L(j,j);
        end
        %ypologizw to athroisma sum2
        for t = 1:i-1
            sum2 = sum2 + L(i,t)^2;
        end
        L(i,i) = sqrt(A1(i,i) - sum2);
    end
    
    L = single(L);
    %L2 einai o L^t(anstrofos) pinakas tou L, dhladh oi grammes eginan stiles kai oi
    %sthles grammes
    L2 = L';
    L2 = single(L2);
    %tha ftiaxoume to dianysma b
    %orismata a, r, c apo ta orismata stin synartisi
    
    %arxika to gemizoume me to c
    b(1:n) = c;
    b = b';
    
    %vazoume sto dianisma b thn arxikh kai telikh timi(a apo to orisma tis synartisis)
    b(1) = a;
    b(n) = a;
    
    %gemizoume to dianisma me thn r timi
    b(2,1) = r;
    b(n-1,1) = r;
    %telos, ftiaxame to dianisma b
    
    b = single(b);

    %tha ypologisoume twra to Y apo to L*Y = b
    %Y = inv(L)*b;
    %Y;
    %pros ta empros antikatastasi
    for j=1:n
       sum3 = 0;
       %ginetai oikonomia xronou!!!
       if j > 3
        p = j-2;
       else
        p = 1;
       end
       for w = p:j-1
        sum3 = sum3 + L(j,w)*Y(w,1);
       end
       Y(j,1) = (b(j,1) - sum3)/L(j,j);  
    end
    Y = single(Y);
    
    %ypologiszoume to x apo to L(anastrofo)*X = Y
    %X = inv(L2)*Y;
    X(1:n) = 0;
    X = X';
    %ypologismos tou diamysmatos X me antikatastasi pros ta pisw
    for j = n:-1:1
       sum3 = 0;
       %ginetai oikonomia xronou!!!
       if j < n-2
        p = j+2;
       else
        p = n;
       end
       for w = p:-1:j
        sum3 = sum3 + L2(j,w)*X(w,1);
       end
       X(j,1) = (Y(j,1) - sum3)/L2(j,j);
    end
    X = single(X)

    %otan to n einai megalo gia na ektypwsw tous panw-katw
    %5x5 pinakes kanoume

    %L(1:5, 1:5) up
    %L(n-5:n, n-5:n) down
    %Y(1:5) up
    %Y(n-5:n) down
    %X(1:5) up
    %X(n-5:n) down 
end