# 207-PROJECT

## Table of Contents
- [Team Members](#team-members)
- [Project Overview](#project-overview)
- [Major Features](#major-features)
  - [Creating New Mind Maps](#creating-new-mind-maps)
  - [Exporting Mind Maps](#exporting-mind-maps)
  - [Editing Mind Map Titles](#editing-mind-map-titles)
  - [Adding Text Nodes](#adding-text-nodes)
  - [Fetching Images](#fetching-images)
  - [Adding Image Nodes](#adding-image-nodes)
  - [Connecting Nodes with Lines](#connecting-nodes-with-lines)
  - [Deleting Nodes](#deleting-nodes)
  - [Categorizing Nodes by Color](#categorizing-nodes-by-color)
- [User Stories](#user-stories)
- [Installation](#installation)
- [How to use the program](#how-to-use-the-program)
- [License](#license)

## Team Members
* Ethan Chiu (ethanch1u)
* Jackie Liang (jackieliang5985)
* Sudharshan Palaniyappan (sudharshanpal)
* Ujjvel Lijo (Ujivl)
* Eric Lee (itsyaboieric)

## Project Overview

**Mind Map Board Generator** is a virtual mind-mapping application designed to help users **create, connect, and organize ideas** through a **graphical interface**. The application allows users to visually represent their thoughts, plans, and ideas in an easy-to-manage format. The system features a variety of interactive tools to enable users to:

- **Create and edit mind maps**: Users can start with a blank mind map and build it by adding different elements such as nodes and connections.
- **Add draggable text and image nodes**: Users can add both text and image-based nodes to represent ideas and link them together.
- **Connect nodes with lines**: Connections between nodes can be easily made to show relationships and hierarchies of ideas.
- **Categorize and organize by color**: Nodes and lines can be color-coded to group related ideas, show importance, or improve the overall organization.
- **Export mind maps**: Finished mind maps can be exported in formats like PDF, PNG, or JPEG for sharing and external use.

This project aims to provide a **seamless and intuitive experience** for **brainstorming, planning**, and **organizing information visually**, offering users a powerful tool for managing their ideas in a dynamic and engaging way.

## Major Features
- [Creating New Mind Maps](#creating-new-mind-maps)
- [Exporting Mind Maps](#exporting-mind-maps)
- [Editing Mind Map Titles](#editing-mind-map-titles)
- [Adding Text Nodes](#adding-text-nodes)
- [Fetching Images](#fetching-images)
- [Adding Image Nodes](#adding-image-nodes)
- [Connecting Nodes with Lines](#connecting-nodes-with-lines)
- [Deleting Nodes](#deleting-nodes)
- [Categorizing Nodes by Color](#categorizing-nodes-by-color)

## User Stories

### Creating and Exporting Mind Maps

- **User Story:** John opens a new mind map and exports his mind map after he's done.
  - **Assigned to**: Team

- #### Use Cases:
  ### **Creating New Mind Maps**
  - Users can create a blank mind map to start their brainstorming process.
    - **Interactor**: Handles logic for initializing a new mind map.
    - **Controller**: Prepares a fresh state for a new mind map.
    - **Presenter**: Updates the user interface to display the new map.
  ### **Exporting Mind Maps**
  - The user can open a new mind map and then export the current state of the mind map, including nodes and connections, into PDF/PNG/JPEG format.
    - **Interactor**: Handles the logic for exporting the current mind map.
    - **Controller**: Manages state and coordinates the export operation.
    - **Presenter**: Provides feedback to the user upon successful export (e.g., confirmation message).
  ### **Editing Mind Map Titles**
  - Users can edit the title of their mind map to reflect its content better.
    - **Interactor**: Validates and processes title changes.
    - **Controller**: Updates the title based on user input.
    - **Presenter**: Updates the displayed title in the user interface.

### Adding Text-Based Nodes

- **User Story:** John wants to make text nodes on the mind map board.
  - **Assigned to**: Uji

#### Use Case:
- ### **Adding Text Nodes**
  - The user makes a text node and positions it by holding the left-click button on the node to move it around the board to organize their mind map visually.
  - **Interactor**: Listens for user input to create text nodes.
  - **Controller**: Manages node creation and positioning.
  - **Presenter**: Displays text nodes at specified positions on the board.

### Adding Images as Nodes

- **User Story:** John selects an image from the image selection as a draggable node on the board to incorporate visual elements.
  - **Assigned to**: Jackie

#### Use Cases:
- ### **Fetching Images**
  - The user selects an image from the search function using the Unsplash API to be used within an image notepad.
    - **Interactor**: Manages the process of fetching the image file.
    - **Controller**: Handles the user request to fetch and add an image to the board.
    - **Presenter**: Ensures the fetched image is ready for placement on the board.
- ### **Adding Image to Notepad**
  - After fetching, the image is placed on the board as a draggable node, allowing further customization and positioning.
    - **Interactor**: Handles the addition of the image to the board.
    - **Controller**: Manages the positioning of the image as a draggable node.
    - **Presenter**: Renders the image node at the specified location on the board.

### Connecting Nodes with Lines

- **User Story:** John wants to create a connected text node that links to an existing node in the mind map.
  - **Assigned to**: Eric

#### Use Case:
- ### **Connecting Nodes With Lines**
  - The user can create a new text node connected to a parent node by right-clicking on the parent node and creating a new connected node.
    - **Interactor**: Detects starting and ending points for line creation.
    - **Controller**: Establishes the line connection between nodes.
    - **Presenter**: Renders lines with specified colors between nodes.

### Deleting Nodes

- **User Story:** John removes irrelevant nodes, which automatically disconnects all the lines connecting the node to others.
  - **Assigned to**: Sudharsian

#### Use Case:
- ### **Deleting Nodes**
  - The user right-clicks a node to delete it, and the system automatically removes both the node and all lines connecting it to other nodes, ensuring a clean and organized mind map.
    - **Interactor**: Detects the node selected for deletion and removes the node and its associated lines (edges) in one operation.
    - **Controller**: Validates the deletion request and updates the mind map state by removing the node and all connected lines.
    - **Presenter**: Updates the display to reflect the removal of the node and its lines, ensuring the mind map is visually consistent.

### Categorizing Nodes by Color

- **User Story:** John assigns colors to nodes and lines to group related ideas or indicate importance.
  - **Assigned to**: Ethan

#### Use Case:
- ### **Categorizing Nodes by Color**
  - The user customizes the color of nodes and lines to categorize or prioritize information visually.
    - **Interactor**: Receives user-selected colors for nodes or lines.
    - **Controller**: Applies color changes to selected nodes or lines.
    - **Presenter**: Updates the display with the chosen colors.

## Installation
* Support for Windows, MacOS, and Linux
* Clone the repository using the command:  
  `git clone https://github.com/jackieliang5985/207-PROJECT.git`
* To run the program, ensure you have Java installed on your system. You can install the latest version of Java from Oracle's Java SE Downloads.
* Java Development Kit (JDK) 11 or higher is recommended for running the program.

## How to use the program
* To begin creating your mind map, enter a title for your mind map and click on "Create New Mindmap"
* From there, you can choose to change the title if needed, or continue to the mind map board
* On the mind map board, all actions are accessible within a dropdown menu which can be opened up by right-clicking anywhere on the board.
* **Add Image Post It**: Search for a desired image through text input. Select an image that shows up upon searching to add it to the board.
* **Add Text Post It**: Enter the text you want to write on the note, and it will appear on the board with a default color of orange.
* Notes can be repositioned by holding left-click over them and dragging them across the board.
* **Change Color**: You must right-click over the target note to be able to perform this action. Select a color from the color chooser, which will update the color of the note accordingly.
* **Add Connection**: You must right-click over the target note to be able to perform this action. Then, click on the note you want to connect the target note to, which will create a line connecting the two notes.
* **Delete Post It**: You must right-click over the target note to be able to perform this action. The note, as well as any connections it is involved in will be deleted from the mind map.
* **Save**: You can export the mind map as a .png, .jpg, or .pdf file.
* **Logout**: Exit the mind map and return to the create new mind map page.

## License
* See the LICENSE file for more details.
