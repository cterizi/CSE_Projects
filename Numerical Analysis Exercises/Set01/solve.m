function [  ] = solve(n, A, b)
    %Pairnoume to apotelesma gia to an o pinakas A exei aytsthra kyriarxikh
    %diagwnio h oxi
    r = dominantDiagonal(A, n);
    
    %Kanoume ton elegxo me to apotelesma pou maw edwse h synartisi kai ama
    %einai 'yes' tote exei o pinakas A aysthra kyriarxikh diagwnio
    %diaforetika emfanizei mhnyma oti den exei kai termatizei to programma
    if strcmp(r, 'no')
        a = 'O pinakas A den exei austhra kyriarxikh diagwnio'
    else
        %emafanizoume ena mhnyma oti o pinakas mas exei aysthra kyriarxikh
        %diagwnio
        a = 'O pinakas A exei austhra kyriarxikh diagwnio'
        %Kaloume tin synarthsh pou exoume ftiaxei kai maw epistrefei tous
        %pinakes L, U
        [L,U] = lu(A, n);
        %Kaloume tin synartisi solveSystems h opoia epilyei ta sythmata pou
        %prepei kai maw epistrefei to dianysma x, opou einai kai h lysh
        x = solveSystems(L,b,U,n)
    end
end