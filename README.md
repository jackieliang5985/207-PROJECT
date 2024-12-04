# CSC207 Project: MapMyMind

## Table of Contents
- [Team Members](#team-members)
- [Project Overview](#project-overview)
- [Major Features](#major-features)
  - [Creating New Mind Maps](#creating-new-mind-maps)
  - [Exporting Mind Maps](#exporting-mind-maps)
  - [Editing Mind Map Titles](#editing-mind-map-titles)
  - [Adding Text Notes](#adding-text-notes)
  - [Fetching Images](#fetching-images)
  - [Adding Image Notes](#adding-image-to-notepad)
  - [Connecting Notes with Lines](#connecting-notes-with-lines)
  - [Deleting Notes](#deleting-notes)
  - [Categorizing Notes by Color](#categorizing-notes-by-color)
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

**MapMyMind** is a virtual mind-mapping application designed to help users **create, connect, and organize ideas** through a **graphical interface**. The application allows users to visually represent their thoughts, plans, and ideas in an easy-to-manage format. The system features a variety of interactive tools to enable users to:

- **Create and edit mind maps**: Users can start with a blank mind map and build it by adding different elements such as notes and connections.
- **Add draggable text and image notes**: Users can add both text and image-based notes to represent ideas and link them together.
- **Connect notes with lines**: Connections between notes can be easily made to show relationships and hierarchies of ideas.
- **Categorize by color**: Notes can be color-coded to group related ideas, show importance, or improve the overall organization.
- **Export mind maps**: Finished mind maps can be exported in formats like PDF, PNG, or JPEG for sharing and external use.

This project aims to provide a **seamless and intuitive experience** for **brainstorming, planning**, and **organizing information visually**, offering users a powerful tool for managing their ideas in a dynamic and engaging way.

## Major Features
- [Creating New Mind Maps](#creating-new-mind-maps)
- [Exporting Mind Maps](#exporting-mind-maps)
- [Editing Mind Map Titles](#editing-mind-map-titles)
- [Adding Text Notes](#adding-text-notes)
- [Fetching Images](#fetching-images)
- [Adding Image Notes](#adding-image-to-notepad)
- [Connecting Notes with Lines](#connecting-notes-with-lines)
- [Deleting Notes](#deleting-notes)
- [Categorizing Notes by Color](#categorizing-notes-by-color)

## User Stories

### Creating and Exporting Mind Maps

- **User Story:** John opens a new mind map and exports his mind map after he's done.
  - **Assigned to**: Team

- #### Use Cases:
  #### **Creating New Mind Maps**
  - Users can create a blank mind map to start their brainstorming process.
  
  #### **Exporting Mind Maps**
  - The user can open a new mind map and then export the current state of the mind map, including nodes and connections, into PDF/PNG/JPEG format.

  #### **Editing Mind Map Titles**
  - Users can edit the title of their mind map to reflect its content better.

### Adding Text-Based Notes

- **User Story:** John wants to make text notes on the mind map board.
  - **Assigned to**: Ujjvel

- #### Use Case:
  #### **Adding Text Notes**
  - The user makes a text note and positions it by holding the left-click button on the note to move it around the board to organize their mind map visually.

### Adding Image-Based Notes

- **User Story:** John selects an image from the image selection as a draggable note on the board to incorporate visual elements.
  - **Assigned to**: Jackie

- #### Use Cases:
  #### **Fetching Images**
  - The user selects an image from the search function using the Unsplash API to be used within an image notepad.

  #### **Adding Image to Notepad**
  - After fetching, the image is placed on the board as a draggable note.

### Connecting Notes with Lines

- **User Story:** John wants to create a connection between multiple existing notes in the mind map.
  - **Assigned to**: Eric

- #### Use Case:
  #### **Connecting Notes With Lines**
  - The user can connect two notes by right-clicking on one note and then clicking on another note to form a line between the two notes.

### Deleting Notes

- **User Story:** John removes irrelevant notes, which automatically disconnects all the lines connecting the note to others.
  - **Assigned to**: Sudharshan

- #### Use Case:
  #### **Deleting Notes**
  - The user right-clicks a note to delete it, and the system automatically removes both the note and all lines connecting it to other notes, ensuring a clean and organized mind map.

### Categorizing Notes by Color

- **User Story:** John assigns colors to notes and lines to group related ideas or indicate importance.
  - **Assigned to**: Ethan

- #### Use Case:
  #### **Categorizing Notes by Color**
  - The user customizes the color of notes and lines to categorize information visually.

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
