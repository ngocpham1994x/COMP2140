/************************************************
* Adjacency list representation of a graph    *
*                                               *
************************************************/

import java.io.*;
import java.util.*;
import javax.swing.*;


public class GraphAL
{
  /******************************************************************
  * private Edge class                                              *
  *                                                                 *
  * An "Edge" is an edge from some vertex i to another vertex j.    *
  * The edge is represented by a node in a linked list for          *
  * vertex i (the edge is FROM vertex i).  The node contains        *
  * vertex j (the edge is TO vertex j).                             *
  ******************************************************************/
  private class Edge
  {
    public int toVertex;  // The edge is TO vertex "toVertex".
    public Edge next; // The next edge in the linked list for vertex i.

    // Edge constructor
    public Edge( int j, Edge nextEdge )
    {
      toVertex = j;
      next = nextEdge;
    } // end Edge constructor

  } // end class Edge



  private Edge[] adjacent; // Adjacency list is an array of linked lists.
                           // One linked list for each vertex.
                           // The linked list for vertex i contains
                           // all the edges from vertex i to another vertex.
                           // If there is an edge from vertex i to
                           // vertex j, then the linked list for vertex i
                           // contains a node (called "Edge", not "Node"
                           // in this implementation) containing vertex j.
                           // The linked lists are ordered, ordinary
                           // linked lists with no dummy nodes.
  private int numVertices; // Number of vertices in the graph
                           // (number of linked lists in the adjacency list).
  private boolean[] visited; // For traversals.


  /*******************************************************************
  * GraphAL constructor                                              *
  *                                                                  *
  * Purpose: Construct a graph with nVert vertices and no  edges.    *
  *******************************************************************/
  public GraphAL( int nVert )
  {
      int i;   // for-loop index to loop through vertices.

      numVertices = nVert;

      // Create adjacency list "adjacent" and initiallize it
      // to have no edges.

      adjacent = new Edge[numVertices];
      for (i= 0; i < numVertices; i++)
      {
        // Vertex i currently has no edges going out of it.
        // Therefore, the linked list for vertex i is currently empty.

	adjacent[i] = null;
       }
       visited = new boolean[ numVertices ];
       // Every traversal will have to initiallize visited,
       // so we won't do it here.

  } // end GraphAL constructor

  /*********************************************************************
  * addEdge                                                            *
  *                                                                    *
  * Purpose: Add an edge from vertex "from" to vertex "to".            *
  *          (Adds a new "Edge" node at the front of the linked        *
  *          list for vertex "from".  The new "Edge" node contains     *
  *          vertex "to".  The linked lists are ordered, ordinary    *
  *          linked lists with no dummy nodes.)                        *
  *********************************************************************/
  public void addEdge( int from, int to )
  {
    Edge prev, curr;

    if (0 <= from && from < numVertices)
    {
      if (0 <= to && to < numVertices)
      {
        prev = null;
        curr = adjacent[from];
        while ( (curr != null) && (curr.toVertex < to) )
        {
          prev = curr;
          curr = curr.next;
        }
        if ( ((curr != null) && (curr.toVertex != to))  || (curr == null))
        { 
          // didn't find the edge already inserted, so insert

          if ( prev == null )
            adjacent[from] = new Edge( to, adjacent[from] );
          else
            prev.next = new Edge( to, curr );
        }
      }
      else
        System.out.println( "Error: Can't add an edge to vertex " + to
                            + " when graph only contains " + numVertices
                            + " vertices." );
    }
    else
      System.out.println( "Error: Can't add an edge from vertex " + from
                          + " when graph only contains " + numVertices
                          + " vertices." );
  } // end method addEdge


  /*********************************************************************
  * main                                                               *
  *********************************************************************/
  
  public static void main( String[] args )
  {
    GraphAL G; // Directed graph
    int numVertices; // Number of vertices in G
    
    JFileChooser pickFile = new JFileChooser(); // Dialog box to pick
                                                // input file.
    FileReader theFile; // The file to be read (character file).
    BufferedReader in;  // Read text from a character-input stream.

    String inLine; // A line of input from BufferedReader in.
    String token;    // The next element from a line of input.
    StringTokenizer tokens; // To get the elements from a line of input.

    int from; // The vertex FROM which an edge goes out.
    int to; // The vertex TO which an edge goes.

    G = null;

    System.out.println( "COMP 2140 Digraph practice question, Winter 2011" );
    System.out.println( );


    // First, read in the directed graph.

    try
    {
      // Retrieve the file to be read.

      if (pickFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      {
        theFile = new FileReader( pickFile.getSelectedFile() );
        in = new BufferedReader( theFile );

        // First line contains the number of vertices in the graph.

        inLine = in.readLine();
        tokens = new StringTokenizer( inLine );
        numVertices = Integer.parseInt( tokens.nextToken() );

        // Create a directed graph with numVertices vertices and no edges.
	
        G = new GraphAL( numVertices );

	// Each following line contains an edge from vertex "from" to
        // vertex "to".

        inLine = in.readLine();
        while (inLine != null)
        {
          // Another edge.

          tokens = new StringTokenizer( inLine );
          from = Integer.parseInt( tokens.nextToken() );
          to = Integer.parseInt( tokens.nextToken() );
          G.addEdge( from, to );

          // Read in next edge (if one exists).

          inLine = in.readLine();
        } // end while (inLine != null)

        in.close();

      } // end if pickFile.showOpenDialog()

    } // end try

    catch (IOException ex)
    {
      System.out.println( "I/O error: " + ex.getMessage() );
    }

    // Now, print the in-degree of every vertex.

    G.printIndegrees( );

    // Finally, print the vertices you visit on
    // a depth-first search starting from vertex 0.

    G.printInTraversal( 0 );

    System.out.println();
    System.out.println( "Program completed normally." );

    System.exit(0);
    
  } // end method main



  /******************************************************************
  * printIndegrees                                                  *
  *                                                                 *
  * Purpose: Print the in-degree of every vertex in the graph.      *
  ******************************************************************/
  public void printIndegrees()
  {

    /******************* YOUR CODE GOES HERE *********************/

  } // end method printIndegrees


  /******************************************************************
  * printInTraversal                                                *
  *                                                                 *
  * Purpose: Print the vertices visited in depth-first traversal    *
  *          starting at vertex startVertex.                        *
  *          - print out a header for the vertices                  *
  *          - initiallize the visited array to all false           *
  *          - call the recursive traversal method, which prints    *
  *            out each vertex it visits on a separate line.        *
  ******************************************************************/
  public void printInTraversal( int startVertex )
  {
    int i;

    System.out.println();
    System.out.println( "Vertices visited starting from " + startVertex );
    System.out.println();

    for ( i = 0; i < numVertices; i++ )
      visited[i] = false;

    recursiveTraversal( startVertex);
  } // end method PrintInTraversal

  /******************************************************************
  * recursiveTraversal                                              *
  *                                                                 *
  * Purpose: Do a recursive depth-first traversal, printing each    *
  *          vertex as you visit it, starting at vertex startVertex.*
  *                                                                 *
  ******************************************************************/
  private void recursiveTraversal( int currVertex )
  {

    /******************* YOUR CODE GOES HERE *********************/

  } // end method recursiveTraversal




} // end class GraphAL
