<h1>Spatial Partition</h1>

<h2>Intent</h2>

As said <a href = "http://gameprogrammingpatterns.com/spatial-partition.html">here</a>, 

"Efficiently locate objects by storing them in a data structure organized by their positions."

<h2>Explanation and Application</h2>

The idea behind the Spatial Partition Design Pattern is to enable efficient location of objects by storing them in a data structure that is organised by their positions. This is especially useful in the gaming world, where one may need to look up all the objects within a certain boundary, or near a certain other object. This can be used to store moving and static objects, though in order to keep track of the moving objects, their positions will have to be reset each time they move. This would mean having to create a new instance of the data structure each time an object moves, which would use up additional memory, and so this pattern should only be used if the number of objects to keep track of is large and one does not mind trading memory for speed.
In our implementation, we use the quadtree data structure which will reduce the time complexity of finding the objects within a certain range from O(n^2) to O(nlogn), increasing the speed of computations immensely in case of large number of objects, which will have a positive effect on the video rendering speed.

<h2>Tutorials (Credits)</h2>

<a href = "http://gameprogrammingpatterns.com/spatial-partition.html">Spatial Partition (Game Programming patter/Optimisation pattern)</a>
<a href = "https://www.youtube.com/watch?v=OJxEcs0w_kE">Quadtree tutorial Part 1</a>
<a href = "https://www.youtube.com/watch?v=QQx_NmCIuCY">Quadtree tutorial Part 2</a>
<a href = "https://www.youtube.com/watch?v=z0YFFg_nBjw">Quadtree tutorial Part 3</a>

