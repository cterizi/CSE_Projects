function [  ] = conjugate_gradients( x,y,z,n,a,r,c,e )
    %Ypothetw pws o pinakas einai symmetrikos kai thetika orismenos kai
    %epomenos den kanw kapoion elegxo sxetika me ayto
    
    %ftiaxnw ton pinaka A, arxika ton gemizw me 0
    A=zeros(n);
    
    %trid(x, y, z)
    %gemizoume thn kyria diagwnio me to x
    for i = 1:n
        A(i,i) = x;
    end
    
    %gemizw ton pinaka me to y
    for i = 1:n-1
        A(i,i+1) = y;
    end
    for i = 2:n
       A(i,i-1) = y; 
    end
    
    %gemizw ton pinaka me to z
    for i = 1:n-2
        A(i, i+2) = z;
    end
    for i = 3:n
        A(i, i-2) = z;
    end
    %telos gemismatos pinaka A
    
    %Ftiaxnw ton dianysma b, arxika to gemizoume me to c
    b(1:n) = c;
    b = b';
    
    %vazoume sto dianisma b thn arxikh kai telikh timi(a apo to orisma tis synartisis)
    b(1) = a;
    b(n) = a;
    
    %gemizoume to dianisma me thn r timi
    b(2,1) = r;
    b(n-1,1) = r;
    %telos, ftiaxame to dianisma b
    
    %Arxikopoiw to dianysma X(0) sto mhdeniko dianysma
    for i = 1:n
       X(i,1) = 0; 
    end
    %telos arxikopoihshw tou dianysmatos X
    
    %Arxikopoihsh tou dianysmatos r_0(0) sto dianysma b
    for i = 1:n
       r_0(i,1) = b(i,1); 
    end
    %Telos arxikopoihshs tou dianysmatos r_0

    %Arxikopoihsh tou dianismatos p_1 = r_0
    for i = 1:n
       p_1(i,1) = r_0(i,1); 
    end
    %Telos arxikopoihshs
    for i = 1:n
           res(i,1) = 0;
           if i == 1
               start_step = 1;
               end_step = 3; 
           end 
           if i == 2
               start_step = 1;
               end_step = 4;
           end
           if i == 3
               start_step = 1;
               end_step = 5; 
           end
           if ((i >= 4) & (i < n-2))
               start_step = i-2;
               end_step = start_step + 4;
           end
           if (i == n-2)
              start_step = i-2;
              end_step = n; 
           end
           if (i == n-1)
              start_step = i-2;
              end_step = n; 
           end
           if (i == n)
              start_step = i-2;
              end_step = n; 
           end
           for j = start_step:end_step
               res(i,1) = res(i,1) + A(i,j)*p_1(j,1);
           end
        end
    a = dot(r_0,r_0)/dot(res,p_1);
    X = X + a*p_1;
    r_1 = r_0 - a*res;
    k = 1; %Kaname tin prwth epanalhpsh
    
    while(norm(r_0) > e && k < n)
        k = k + 1;
        b_k = dot(r_1,r_1)/dot(r_0,r_0);
        p_1 = r_1 + b_k*p_1;
        for i = 1:n
           res(i,1) = 0;
           if i == 1
               start_step = 1;
               end_step = 3; 
           end 
           if i == 2
               start_step = 1;
               end_step = 4;
           end
           if i == 3
               start_step = 1;
               end_step = 5; 
           end
           if ((i >= 4) & (i < n-2))
               start_step = i-2;
               end_step = start_step + 4;
           end
           if (i == n-2)
              start_step = i-2;
              end_step = n; 
           end
           if (i == n-1)
              start_step = i-2;
              end_step = n; 
           end
           if (i == n)
              start_step = i-2;
              end_step = n; 
           end
           for j = start_step:end_step
               res(i,1) = res(i,1) + A(i,j)*p_1(j,1);
           end
        end
        a = dot(r_1,r_1)/dot(res,p_1);
        X = X + a*p_1;
        r_0 = r_1;
        r_1 = r_1 - a*res;
    end
    X
    k
end