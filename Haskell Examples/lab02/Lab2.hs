
-----------------------------------------------------------------------------------------
-- ASKHSH 1

update :: Int->[Int]->[Int]

update n s  
	= update1 n s
    
	
update1 :: Int->[Int]->[Int]
update1 n [] = [n] 
update1 n (h:t)
		| n==h = update1 n t 
		| otherwise = h : update1 n t
-----------------------------------------------------------------------------------------   
-- ASKHSH 2

anagram :: String->String->Bool  
  		
anagram w1 w2 = anagram1 x1 x2
	where 
		y1 = break1 w1
		y2 = break1 w2
		x1 = mergeSort y1
		x2 = mergeSort y2
		
anagram1 :: [String]->[String]->Bool

anagram1 [] [] = True
anagram1 [] a = False
anagram1 a [] = False 
anagram1 (h1:t1)(h2:t2) = if h1==h2 then anagram1 t1 t2 else False

--Code of BREAK1 , string converts into a list of char[string]		
break1 :: String -> [String]

break1 s = map (\c -> [c]) s
-----------------------------------------------------------------------------------------
     
-- ASKHSH 3

fromTo :: Int->Int->[u]->[u]                         

fromTo i j s = fromToMine 1 i j s

fromToMine :: Int->Int->Int->[u]->[u]
fromToMine ino i j [] =[]
fromToMine ino i j s1@(h:t)
	| ino >= i && ino <= j = h : fromToMine (ino+1) i j t 
	| ino > j = []
	| otherwise = fromToMine (ino+1) i j t

-----------------------------------------------------------------------------------------
     
-- ASKHSH 4

hosum :: (Int->Int)->(Int->Int)     

hosum f = (\a -> sumF f (abs a) (abs a))   

sumF :: (Int->Int)->Int->Int->Int
sumF  f n p
	| n == ((-1)*p) = f n
	|otherwise = sumF f (n-1) p + f n 
-----------------------------------------------------------------------------------------
     
-- ASKHSH 5                                                              

--Code of MineApply
apply :: Ord u =>  [(v->u)] -> [v] -> [u]
apply [] s = []
apply  s1@(h1:t1) s =  outCommon (mergeSort (myApply h1 s ++ apply t1 s))

--Code of MyApply
myApply :: Ord u => (v->u)->[v]->[u]
myApply f [] = []
myApply f s1@(h:t) = f h : myApply f t

--Code of OutCommon
outCommon :: Ord u => [u]->[u]
outCommon [] =[]
outCommon [a] = a:[]
outCommon (h1:h2:t)
	| h1 /= h2 = h1 : outCommon (h2:t)
	| otherwise = outCommon(h2:t)
	
--Code of MERGE
merge :: Ord u =>  [u]->[u]->[u]

merge [] ys  = ys
merge xs []  = xs
merge xs@(x:xt) ys@(y:yt) 
	| x <= y    = x : merge xt ys
    | otherwise = y : merge xs yt
	
--Code of SPLIT
split :: Ord u => [u]->([u],[u])
						  
split (x:y:zs) = let (xs,ys) = split zs in (x:xs,y:ys)
split [x] = ([x],[])
split []= ([],[])

--Code of MERGE_SORT
mergeSort :: Ord u => [u]->[u] 

mergeSort []  = []
mergeSort [x] = [x]
mergeSort xs  = let (as,bs) = split xs
                in merge (mergeSort as) (mergeSort bs)



-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

-- MHN TROPOPOIHSETE TO PARAKATW TMHMA KWDIKA 
-- XRHSIMEYEI GIA ELEGXO TWN SYNARTHSEWN POY 8A GRAPSETE

computeScore :: (Eq n) => [n]->[n]->Int
computeScore a b = foldr (+) 0 (map (\n -> if n then 1 else 0) [x==y | (x,y) <- (p a b)])
            where p r s = if null r then [] else (head r,head s) : p (tail r) (tail s)  


score1 :: Int
score1 = computeScore [update n s | (n,s)<-p] q
         where p = [(1,[]),(2,[2]),(3,[3,4,5,6,7,8]),(4,[2,3,4,5,6,7]),
                    (5,[1,2,3,4,5]),(6,[1,2,5,8]),(7,[7,7,7,7,3,4,5,8]),
                    (8,[1,2,3,4,5,8,8,8,8]),(9,[9,9,9,9,9,9,9,9]),
                    (10,[10,2,10,3,5,10,7,10,8,10])]
               q = [[1],[2],[4,5,6,7,8,3],[2,3,5,6,7,4],[1,2,3,4,5],[1,2,5,8,6],
                   [3,4,5,8,7],[1,2,3,4,5,8],[9],[2,3,5,7,8,10]]


score2 :: Int
score2 = computeScore [anagram w1 w2 | (w1,w2)<-p] q
         where p = [("",""),("","YES"),("abcd","abcd"),("cinema","iceman"),
                    ("0123","310"),("=--++","==--+"),("==--++","+-+=-="),
                    ("....",".."),("xxxxx0","0xxxxx"),("0*0*0*","*00**0")]
               q = [True,False,True,True,False,False,True,False,True,True]

score3 :: Int
score3 = score3a + score3b + score3c + score3d

score3a :: Int
score3a = computeScore [fromTo i j s | (i,j,s)<-p] q
          where p = [(3,7,[1..10]),(1,7,[1..10]),(4,10,[1..10]),(1600,1600,[0..]),(1,2,[])]
                q = [[3,4,5,6,7],[1,2,3,4,5,6,7],[4,5,6,7,8,9,10],[1599],[]]


score3b :: Int
score3b = computeScore [fromTo i j s | (i,j,s)<-p] q
          where p = [(20,30,['a'..'z']),(12,20,"Haskell")]
                q = ["tuvwxyz",""]


score3c :: Int
score3c = computeScore [fromTo i j s | (i,j,s)<-p] q
          where p = [((-4),6,[0, 25..]),((-18),(18),[0,45..200])]
                q = [[0,25,50,75,100,125],[0,45,90,135,180]]


score3d :: Int
score3d = computeScore [fromTo i j s | (i,j,s)<-p] q
          where p = [((-4),(-3),[False,True])]
                q = [[]]



score4 :: Int
score4 = computeScore [hosum f n | (f,n)<-p] q
         where p = [((\x->1),0),((\x->1),5),((\x->1),(-8)),((\x->x),10),
                    ((\x->abs x),10),((\x->x*x),12),((\x->2^(abs x)),9),
                    ((\x->x `mod` 3),1000),((hosum (\x->1)),7),((hosum (\x->x^2)),4)]
               q = [1,11,17,0,110,1300,2045,2001,127,200]
               

score5 :: Int
score5 = score5a + score5b + score5c


score5a :: Int
score5a = computeScore [apply r s | (r,s)<-p] q
          where p = [([abs],[-1]),([(^2)],[1..5]),([(^2),(^3),(^4),(2^)],[10]),
                     ([(^0),(0^),(\x->div x x),(\x->mod x x)],[1..1000]),
                     ([(^2),(^3),(^4),(2^)],[2..8]),([(\x->mod x 10), (\x->rem x 10)],[-100..100]),
                     ([(*5)],(apply [(`div`5)] [1..100]))]
                q = [[1],[1,4,9,16,25],[100,1000,1024,10000],[0,1],
                     [4,8,9,16,25,27,32,36,49,64,81,125,128,216,256,343,512,625,1296,2401,4096],
                     [-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9],
                     [0,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100]]


score5b :: Int
score5b = computeScore [apply r s | (r,s)<-p] q
          where p = [([head,last],["abc","aaaa","cbbc","cbbca"]),
                     ([head.tail,last.init],["abc","aaaa","cbbc","cbbca"])]
                q = ["ac","abc"]

score5c :: Int
score5c = computeScore [apply r s | (r,s)<-p] q
          where p = [([reverse,(++"ing"),reverse.(++"ing"),(++"ing").reverse],["play","do"])]
                q = [["doing","gniod","gniyalp","od","oding","playing","yalp","yalping"]]


score :: Int
score = score1+score2+score3+score4+score5


-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------