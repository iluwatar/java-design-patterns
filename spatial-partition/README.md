<h1>Spatial Partition</h1>

<h2>Intent</h2>

As said <a href = "http://gameprogrammingpatterns.com/spatial-partition.html">here</a>, to <b><em>"efficiently locate objects by storing them in a data structure organized by their positions"</em></b>.

<h2>Explanation and Applications</h2>

<p>
 The idea behind the <b><em>Spatial Partition</em></b> design pattern is to enable quick location of objects using a data structure (like a quadtree) that is organised by their positions. This is especially useful in the <b><em>gaming world</em></b>, where one may need to look up all the objects within a certain boundary, or near a certain other object frequently, and an efficient way to do it would significantly <b><em>improve the gaming experience</em></b>. The data structure can be used to store moving and static objects, though in order to keep track of the moving objects, their positions will have to be reset each time they move. This would mean having to create a new instance of the data structure each time an object moves, which would use up additional memory, and so this pattern should only be used if the number of objects to keep track of is large and one does not mind trading memory for speed. The common data structures used for this design pattern are:</p>
 <p><ul>
  <li>Grid</li>
  <li>Quad tree</li>
  <li>k-d tree</li>
  <li>BSP</li>
  <li>Boundary volume hierarchy</li>
 </ul></p>
</p><p>
In our implementation, we use the quadtree data structure which will reduce the time complexity of finding the objects within a certain range from O(n^2) to O(nlogn), increasing the speed of computations immensely in case of large number of objects.
 </p>

<h2>Tutorials (Credits)</h2>

<p><a href = "http://gameprogrammingpatterns.com/spatial-partition.html">Spatial Partition (Game Programming patter/Optimisation pattern)</a></p>
<p><a href = "https://www.youtube.com/watch?v=OJxEcs0w_kE">Quadtree tutorial Part 1</a></p>
<p><a href = "https://www.youtube.com/watch?v=QQx_NmCIuCY">Quadtree tutorial Part 2</a></p>
<p><a href = "https://www.youtube.com/watch?v=z0YFFg_nBjw">Quadtree tutorial Part 3</a></p>

