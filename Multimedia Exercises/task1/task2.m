function [] = task2(AC)
    %AC1 = [7, 0, 0, 0, 2, 3, 0, 0, 0, 4, 0, 1, 0, 0, 0, 1, 0, 0, -1, -1, 0, 1, 0, 0, 0, 1, 0];
    %AC2 = zeros(1,36);
    %AC = [AC1, AC2];
    
    [a, b] = size(AC);
    
    disp('(LEVEL, RUN)')
    disp('------------')
    
    count = 0;
    for i = 1:b
       if (i == b)
           disp('END OF BLOCK')
       end
       if (i == 1)
           fprintf('(%d, 0)\n', AC(i))
       else
           if(AC(i) == 0)
               count = count + 1;
           else
               fprintf('(%d, %d)\n', AC(i), count)
               count = 0;
           end
       end
    end
end