function [ x ] = dominantDiagonal( A,n )
    %Ypoprogramma pou elegxei ama enas pinakas exei austhra kyriarxikh
    %diagwnio
    for i=1:n
        sum=0;
        for j=1:n
            if j~=i 
                sum = sum + abs(A(i,j));
            end
        end
        if sum <= abs(A(i,i))
            x = 'yes';
        else
            x = 'no';
            %otan mpei mia fora sto else shmainei oti den exei kyriarxikh
            %diagwnio gia auto kai to kanoune kateutheian break wste na mhn
            %synexisoun oi peretero ypologismoi
            break;
        end
    end
end