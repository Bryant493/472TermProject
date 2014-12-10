javac *.java
printf "\nPlease enter a strategy: "
read strat
printf "\nPlease enter a depth: "
read depth
java Agent2048 $strat $depth