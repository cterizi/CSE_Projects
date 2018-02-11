-- Chryssa Terizi AM:2553
-----------------------------------------------------------------------------------------

-- ASKHSH 1


grade :: Int->Int->Int                           

grade a b 
	| a < 0  = -1
	| a > 100 = -1
	| b < 0 = -1
	| b > 20 = -1
	| c > 47 && a <= 47 = 47
	| c > 47 && c < 50 && a > 47 = 50
	| otherwise = c
	where c = ( ( 8 * a ) `div` 10 ) + b                                         
-----------------------------------------------------------------------------------------
     
-- ASKHSH 2

parking :: (Int,Int)->(Int,Int)->Int            
  
parking (h1,m1) (h2,m2)                                         
	| h <= 3 = 8
	| h <= 6 = 8 + (h-3) * 2
	| otherwise = 14 + (h-6) 
	where h = if m2>m1 then h2 - h1 + 1 else h2 - h1
-----------------------------------------------------------------------------------------
     
-- ASKHSH 3

digits :: Int->Int->Int                         

digits x y 
	| n == 8 = 1000000
	| n == 7 = 100000
	| n == 6 = 8000
	| n == 5 = 300
	| n == 4 = 20
	| n == 3 = 5
	| n == 2 = 1
	| otherwise = 0
	where n = sum1 x y

sum1 :: Int->Int->Int
sum1 x y
	| x==0 && y==0 = 0
	| otherwise = if digita==digitb then 1 + sum1 (x `div` 10) (y `div` 10) else sum1 (x `div` 10) (y `div` 10)
	where 
		digita = x `mod` 10
		digitb = y `mod` 10
-----------------------------------------------------------------------------------------
     
-- ASKHSH 4

search :: Integer->Integer->Integer->Integer     

search a k m = 
		mySearch a k m 1
		
mySearch :: Integer->Integer->Integer->Integer->Integer
mySearch a k m n 
	| ( n + a ) ^ k < m ^ n = n
	| otherwise = mySearch a k m (n+1)                
-----------------------------------------------------------------------------------------
-- ASKHSH 5                                   
sum2015 :: Integer->Integer->Integer             
sum2015 m n = sumChryssa2015 m n m 
	
sumChryssa2015 :: Integer->Integer->Integer->Integer
sumChryssa2015 x y z 
	| x > y = 0
	| otherwise = ( z + x )^ y + sumChryssa2015 ( x + 1) y z
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

-- MHN TROPOPOIHSETE TO PARAKATW TMHMA KWDIKA 
-- XRHSIMEYEI GIA ELEGXO TWN SYNARTHSEWN POY 8A GRAPSETE


computeScore :: Num n => [n]->[n]->Int
computeScore a b = foldr (+) 0 (map (\n -> if n then 1 else 0) [x==y | (x,y) <- (p a b)])
            where p r s = if null r then [] else (head r,head s) : p (tail r) (tail s)  


score1 :: Int
score1 = computeScore [grade a b | (a,b)<-p] q
         where p = [(100,20),(100,0),(0,20),(80,20),(59,10),
                    (48,10),(48,9),(40,20),(20,100),(35,-2)]
               q = [100,80,20,84,57,50,47,47,-1,-1]


score2 :: Int
score2 = computeScore [parking t1 t2 | (t1,t2)<-p] q
         where p = [((13,59),(14,00)),((15,30),(16,30)),
                    ((8,45),(11,15)),((6,15),(9,14)),
                    ((12,22),(15,23)),((16,15),(21,05)),
                    ((10,55),(16,05)),((6,05),(12,15)),
                    ((8,32),(20,28)),((6,00),(22,00))]
               q = [8,8,8,8,10,12,14,15,20,24]


score3 :: Int
score3 = computeScore [digits x y | (x,y)<-p] q
         where p = [(13758455,13758455),(22812509,22852509),
                    (38954421,70954421),(42358921,42358002),
                    (12548706,14520786),(80000012,71800009),
                    (42212553,22125531),(58723493,83463738),
                    (78354623,46237835),(34578364,83648364)]
               q = [1000000,100000,8000,300,20,5,1,0,0,20]

score4 :: Int
score4 = computeScore [search a k m | (a,k,m)<-p] q
         where p = [(0,2,2),(1,2,2),(2,2,2),(2,5,2),(5,2,2),(1,10,3),
                    (1,100,2),(1000,2,3),(100,100,100),(1000,1000,1000)]
               q = [1,6,7,24,8,32,997,13,117,1108]
               

score5 :: Int
score5 = computeScore [sum2015 m n | (m,n)<-p] q
         where p = [(0,1),(0,2),(0,3),(0,10),(1,1),
                   (1,3),(1,5),(3,4),(7,10),(12,12)]
               q = [1,5,36,14914341925,2,99,12200,3697,
                    3981410573826,36520347436056576]

score :: Int
score = score1+score2+score3+score4+score5
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------