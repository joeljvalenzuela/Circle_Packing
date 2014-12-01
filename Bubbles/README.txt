How the program works?

Input:
	The program recieves a set of paramerters formatted as: "radius,name". 		
	The radius is used to determine the size of the circle and the name is 		
	used to identify the circle.

Reading Input:
	The program will parse through the input string. For each line a 	 
	"bubble" object or circle is created and managed by a single entity 		
	known as the "BubbleManager". Diameter, center, and relative 		
	relationship information is stored in the bubble upon its creation.

Sorting:
 	Once the bubbles are created, a merge sort is performed to order the 		
	bubbles. (Used for recursive approach to solution--deprecated)

Algorithm:
	For each circle a point upon the circle's circumference is calculated 		
	using a variant of the parametric equation. A quick collision analysis 		
	is performed and non-collision locations are noted in a Point[]. 

	For every point in the non-collision Point[] a second analysis as to 		
	the circle's distance relationship to other circles is performed. This 		
	relationship is minimalized to cluster circles as closely together as 		
	possible. Once a best position is achieved the bubble's position is 		
	set. 
	
	This is performed for all bubbles in the the Bubble Manager's list of Bubbles.

Visualization:
	Bubbles are created based on their calculated positions in relation to 		
	one another.

Special Instructions:
	Please maximize window before starting "Making Bubbles"

Binary Information:
	Binary can be found at: 
		/Bubbles/dist/Bubbles.jar

	To run the project from the command line, go to the dist folder and
		type the following:

		java -jar "Bubbles.jar" 

Code Documentation
	For javaDoc information please go to:
		/Bubbles/dist/javadoc/index.html

IDE Used: Netbeans

Comments:
	Old parent/child code remains for a recursive approach to the problem.

Further fixes or inhancements that could have been made:
	I tried adding the Panel's border dimensions as a collision so that the
	bubbles wouldn't be created outside of it's allocated space but ran 		
	out of time. This could be further developed.

TODO: 
Fix initial Bubble being overlapped
Fix Maximization issue
Optimize position searching algorithm. Currently O(n^2). This can be more efficient.
Add Dynamic re-positioning