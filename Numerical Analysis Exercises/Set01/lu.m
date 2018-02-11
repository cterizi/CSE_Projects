function [L,U] = lu( A,n )
    %arxikopoioume tous L kai U, o L epeidi exei 1 sthn diagwnio tou ton arxikopoiv opws ton monadiaio pinaka
    %kai ton U ton arxikopoiw olo me mhdenika
    L = eye(n);
    U = zeros(n);
    
    %Gemizoume ton U me ta stoixei apou gnwrizoume
    for i=1:n
        for j=1:n
            if i<j
                U(i,j) = A(i,j);
            end
        end
    end    
    
    %Efarmozoume ton olgorithmo gia na vroume ton pinaka L
    for i = 1:n
        for j = 1:(i-1)
            sum=0;
            for k = 1:(j-1)
                sum = sum + L(i,k)*U(k,j);
            end
            L(i,j) = ( A(i,j) - sum )/U(j,j);
        end 
        L(i,i) = 1;
        
        %Vriskoume ton pinaka U
        for j = i:n
            sum = 0;
            for k = 1:i-1
                sum = sum + L(i,k)*U(k,j);
            end
            U(i,j) = (A(i,j) - sum);
        end
    end 
end