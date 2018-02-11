function [ ] = threshold( )
	image = imread('C:\Users\Chryssa\Documents\cse\7osemester\PsifiakiEpexergasiaEikonas\set1\trikoupi6.png');
	colormap(gray);
	counter = 0;

	for k = 25:28:255
	    localImage = image;
	    localImage(localImage <= k) = 0;
	    localImage(localImage > k) = 1;
	    
	    counter = counter + 1;
	    subplot(3,3,counter)
	    imagesc(localImage)
	end

end
