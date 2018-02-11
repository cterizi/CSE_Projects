function [ ] = thresholdFinal(k)
image = imread('C:\Users\Chryssa\Documents\cse\7osemester\PsifiakiEpexergasiaEikonas\set1\trikoupi6.png');
colormap(gray);
[x,y] = size(image);

for i = 1:1:x
   for j = 1:1:y
       if(image(i,j) <= k)
           image(i,j) = 0;
       else
           image(i,j) = 1;
       end  
   end
end
imagesc(image)
end