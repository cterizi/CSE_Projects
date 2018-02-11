function [] = adaptive(image, geitonia)
    [x, y] = size(image);
    
    if geitonia == 3
        g = 2;
        start = 2;
        endloop = x + 1;
        f = 1;
    else
        if geitonia == 5
            g = 4;
            start = 3;
            endloop = x + 2;
            f = 2;
        else 
            if geitonia == 7
                g = 6;
                start = 4;
                endloop = x + 3;
                f = 3;
            else
                if geitonia == 9
                    g = 8;
                    start = 5;
                    endloop = x + 4;
                    f = 4;
                else 
                   if geitonia == 11
                       g = 10;
                       start = 6;
                       endloop = x + 5;
                       f = 5;
                   else 
                      if geitonia == 13
                          g = 12;
                          start = 7;
                          endloop = x + 6;
                          f = 6;
                      else
                         g = 14;
                         start = 8;
                         endloop = x + 7;
                         f = 7;
                      end
                   end
                end
            end
            
        end
    end
    
    xZeroPad = x + g;
    yZeroPad = y + g;
    
    for i = 1:xZeroPad
        for j = 1:yZeroPad
            tempImage(i,j) = 0;
        end
    end
    
    for i = start:endloop
        for j = start:endloop
           tempImage(i, j) = image(i - f, j - f); 
        end
    end
    
    for i = start:endloop
       for j = start:endloop
          block = tempImage(start - f:start + f, start - f:start + f);
          zMedian = median(median(block));
          zMin = min(min(block));
          zMax = max(max(block));
          A1 = zMedian - zMin;
          A2 = zMedian - zMax;
          if A1 > 0 & A2 < 0
              B1 = tempImage(i, j) - zMin;
              B2 = tempImage(i, j) - zMax;
              if B1 > 0 & B2 < 0
                 finalImage(i - f, j - f) = tempImage(i,j);
              else
                 finalImage(i - f, j - f) = zMedian;
              end
          else
              adaptive(block, geitonia + 2);
          end
%           if i == start & j == start
%              zMax 
%           end
       end
    end
end