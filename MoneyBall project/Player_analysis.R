library(readxl)
library(ggplot2)
library(dplyr)
library(writexl)

batting <- read.csv('R programming/R udemy course files/Udemy course notes/R-Course-HTML-Notes/R-for-Data-Science-and-Machine-Learning/Training Exercises/Capstone and Data Viz Projects/Capstone Project/Batting.csv')

batting$BA <- batting$H / batting$AB

batting$OBP <- (batting$H + batting$BB + batting$HBP)/(batting$AB + batting$BB + batting$HBP + batting$SF)

batting$X1B <- batting$H - batting$X2B - batting$X3B - batting$HR

batting$SLG <- ((batting$X1B) + (2 * batting$X2B) + (3 * batting$X3B) + (4 * batting$HR)) / batting$AB

sal <- read.csv('R programming/R udemy course files/Udemy course notes/R-Course-HTML-Notes/R-for-Data-Science-and-Machine-Learning/Training Exercises/Capstone and Data Viz Projects/Capstone Project/Salaries.csv')

bat <- subset(batting, yearID > 1984)

combo <- merge(bat, sal, by = c('playerID', 'yearID'))

age <- read_excel('R programming/R udemy course files/Udemy course notes/R-Course-HTML-Notes/R-for-Data-Science-and-Machine-Learning/Training Exercises/Capstone and Data Viz Projects/Capstone Project/People.xlsx')

age <- subset(age, select = c('playerID', 'birthYear'))

age$age <- 2001 - age$birthYear

combo_with_age <- merge(combo, age, by = 'playerID')

combo_with_age <- subset(combo_with_age, yearID == 2001)

lost_players <- subset(combo_with_age, playerID %in% c('giambja01', 'damonjo01', 'saenzol01'))

lost_players <- subset(lost_players, yearID == 2001)

lost_players <- subset(lost_players, select = c('playerID', 'age', 'salary', 'AB', 'OBP', 'yearID', 'H', 'X1B', 'X2B', 'X3B', 'HR', 'SLG', 'BA'))

players_age_till22 <- subset(combo_with_age, birthYear >= 1979)

players_age_23to27 <- subset(combo_with_age, birthYear >= 1974)

players_age_23to27 <- subset(players_age_23to27, birthYear <= 1978)

players_age_28to32 <- subset(combo_with_age, birthYear >= 1969)

players_age_28to32 <- subset(players_age_28to32, birthYear <= 1973)

players_age_33to37 <- subset(combo_with_age, birthYear <= 1968)

players_age_33to37 <- subset(players_age_33to37, birthYear >= 1964)

players_age_olderthan_38 <- subset(combo_with_age, birthYear <= 1963)

players_age_till22 <- subset(players_age_till22, salary<=8000000 & AB > 450 & OBP > 0.34)

players_age_till22 <- players_age_till22[, c('playerID', 'age', 'salary', 'AB', 'OBP', 'yearID', 'H', 'X1B', 'X2B', 'X3B', 'HR', 'SLG', 'BA')]

players_age_23to27 <- subset(players_age_23to27, salary <= 8000000 & AB > 450 & OBP > 0.34)

players_age_23to27 <- players_age_23to27[, c('playerID', 'age', 'salary', 'AB', 'OBP', 'yearID', 'H', 'X1B', 'X2B', 'X3B', 'HR', 'SLG', 'BA')]

players_age_23to27 <- head(arrange(players_age_23to27, desc(OBP)), 13)

players_age_28to32 <- subset(players_age_28to32, salary <= 8000000 & AB > 450 & OBP > 0.34)

players_age_28to32 <- players_age_28to32[, c('playerID', 'age', 'salary', 'AB', 'OBP', 'yearID', 'H', 'X1B', 'X2B', 'X3B', 'HR', 'SLG', 'BA')]

players_age_28to32 <- head(arrange(players_age_28to32, desc(OBP)), 14)

players_age_33to37 <- subset(players_age_33to37, salary <= 8000000 & AB > 450 & OBP > 0.34)

players_age_33to37 <- players_age_33to37[, c('playerID', 'age', 'salary', 'AB', 'OBP', 'yearID', 'H', 'X1B', 'X2B', 'X3B', 'HR', 'SLG', 'BA')]

players_age_33to37 <- head(arrange(players_age_33to37, desc(OBP)), 12)

players_age_olderthan_38 <- subset(players_age_olderthan_38, salary <= 8000000 & AB > 450 & OBP > 0.34)

players_age_olderthan_38 <- players_age_olderthan_38[, c('playerID', 'age', 'salary', 'AB', 'OBP', 'yearID', 'H', 'X1B', 'X2B', 'X3B', 'HR', 'SLG', 'BA')]

excel_sheets('Cityu year 1 Summer term/GE 2324 the art and science of data/Group project/Players data.xlsx')

lost_players_df <- read_excel('Cityu year 1 Summer term/GE 2324 the art and science of data/Group project/Players data.xlsx', sheet = 1)

lost_players_df_plot <- ggplot(lost_players_df, aes(x = AB, y = salary)) + geom_point()

players_age_till22 <- ggplot(players_age_till22, aes(x = AB, y = salary)) + geom_point()

players_age_23to27 <- ggplot(players_age_23to27, aes(x = AB, y = salary)) + geom_point()

players_age_28to32 <- ggplot(players_age_28to32, aes(x = AB, y = salary)) + geom_point()

players_age_33to37 <- ggplot(players_age_33to37, aes(x = AB, y = salary)) + geom_point()

players_age_olderthan_38 <- ggplot(players_age_olderthan_38, aes(x = AB, y = salary)) + geom_point()





