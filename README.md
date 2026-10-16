# Artificial Intelligence Lab (IC-709D)

**Student Name:** Vinisha Wagh  
**Roll Number:** IC-2K23-99  
**Course:** IC-709D / IC-703 C | Artificial Intelligence  
**Institute:** International Institute of Professional Studies (IIPS), DAVV Indore  

---

## Repository Overview

This repository contains my laboratory assignments, code implementations, outputs, and documentation for the Artificial Intelligence Lab course.

The repository follows the lab manual directory structure:
- Each laboratory session has its own folder (`Lab1`, `Lab2`, etc.).
- Each folder contains the source code, output files, and a `README.md` with problem statement, algorithm, and analysis.

---

## Lab Index

| Lab | Experiment | Language | Status |
|:---:|:---|:---:|:---:|
| **Lab 1** | [AI Environment Setup & Water Jug Problem](Lab1/) | Java | Completed |
| **Lab 2** | BFS, DFS, Hill Climbing, Best First Search, A* | - | Pending |
| **Lab 3** | Data Preprocessing, EDA, NumPy, Pandas, Visualization | - | Pending |
| **Lab 4** | Knowledge Representation, Rule-Based Systems, Semantic Networks | - | Pending |
| **Lab 5** | Decision Tree Classifier, Model Evaluation | - | Pending |
| **Lab 6** | PyTorch Basics, Linear Regression, Neural Network Fundamentals | - | Pending |
| **Lab 7** | Explainable AI (Grad-CAM), Adversarial AI (FGSM), PCA/SVD | - | Pending |

---

## Folder Structure

```
IC-2K23-99-AI-LAB/
├── Lab1/
│   ├── WaterJugProblem.java       # Water Jug problem implementation in Java
│   ├── outputs/
│   │   └── output.txt             # Console execution output
│   └── README.md                  # Lab report with algorithm, rules, and analysis
├── datasets/                      # Datasets used across experiments
├── models/                        # Saved models / checkpoints
├── docs/                          # Documentation and diagrams
│   └── algorithm-flowcharts/
├── AI Lab.pdf                     # Lab manual and guidelines
├── README.md                      # Repository overview
└── .gitignore                     # Git ignore rules for Java
```

---

## Running Lab 1 (Water Jug Problem)

To compile and run Lab 1:

```bash
# Compile
javac Lab1/WaterJugProblem.java

# Run (interactively prompts for capacities and target)
java -cp Lab1 WaterJugProblem
```