#include "menu.h"
#include <QFile>
#include <QTextStream>
#include <fstream>

namespace game {
Menu::Menu(QWidget* parent, QString name, int& playerScore, int& currenLevel, int& numOfFrames, QString& typeOfControls, QList<QPair<QString, int>> scores)
        : gameScore(playerScore), level(currenLevel), frameRate(numOfFrames), typeOfControl(typeOfControls), playerNameString(name) {
    // Scores could be read in the future.
    makeButtons(parent, name);
}

Menu::~Menu() {
    for (QLabel* l: playersNames) {
        delete l;
    }
    for (QLabel* l: playersScores) {
        delete l;
    }

    delete score;
    delete controls;
    delete speed;
    delete low;
    delete medium;
    delete high;
    delete mouse;
    delete keyboard;


    delete playerName;
    delete playerScoreLabel;
    delete levelLabel;
}

void Menu::makeButtons(QWidget* parent, QString name) {
    QFont font;
    font.setPointSize(15);
    font.setBold(true);

    score = new QPushButton("Score", parent);
    score->setGeometry(QRect(0, 0, 100, 30));
    score->setVisible(false);
    score->setStyleSheet("background-color: red");
    QObject::connect(score, SIGNAL(released()), parent, SLOT(showScore()));

    //CONTROL SETTINGS
    controls = new QPushButton("Controls", parent);
    controls->setGeometry(QRect(parent->width()/2 - 70, parent->height()/2, 200, 30));
    controls->setVisible(false);
    QObject::connect(controls, SIGNAL(released()), parent, SLOT(showControls()));
    controls->setFont(font);
    controls->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");

    mouse = new QPushButton("Mouse", parent);
    mouse->setGeometry(QRect(parent->width()/2 - 70 + 210, parent->height()/2 - 20, 200, 30));
    mouse->setVisible(false);
    QObject::connect(mouse, SIGNAL(released()), parent, SLOT(setMouse()));
    mouse->setFont(font);

    keyboard = new QPushButton("Keyboard", parent);
    keyboard->setGeometry(QRect(parent->width()/2 - 70 + 210, parent->height()/2 + 20, 200, 30));
    keyboard->setVisible(false);
    QObject::connect(keyboard, SIGNAL(released()), parent, SLOT(setKeyboard()));
    keyboard->setFont(font);

    highlightControl();

    //SPEED SETTINGS
    speed = new QPushButton("Speed", parent);
    speed->setGeometry(QRect(parent->width()/2 - 70, parent->height()/2 + 35, 200, 30));
    speed->setVisible(false);
    QObject::connect(speed, SIGNAL(released()), parent, SLOT(showSpeeds()));
    speed->setFont(font);
    speed->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");

    low = new QPushButton("Low", parent);
    low->setGeometry(QRect(parent->width()/2 - 70 + 210, parent->height()/2, 200, 30));
    low->setVisible(false);
    QObject::connect(low, SIGNAL(released()), parent, SLOT(setLowSpeed()));
    low->setFont(font);

    medium = new QPushButton("Medium", parent);
    medium->setGeometry(QRect(parent->width()/2 - 70 + 210, parent->height()/2 + 35, 200, 30));
    medium->setVisible(false);
    QObject::connect(medium, SIGNAL(released()), parent, SLOT(setMediumSpeed()));
    medium->setFont(font);

    high = new QPushButton("High", parent);
    high->setGeometry(QRect(parent->width()/2 - 70 + 210, parent->height()/2 + 70, 200, 30));
    high->setVisible(false);
    QObject::connect(high, SIGNAL(released()), parent, SLOT(setHighSpeed()));
    high->setFont(font);

    highlightSpeed();

    //BACK BUTTON
    back = new QPushButton("Back", parent);
    back->setGeometry(QRect(parent->width()/2 - 70, parent->height()/2 + 70, 200, 30));
    back->setVisible(false);
    back->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
    QObject::connect(back, SIGNAL(released()), parent, SLOT(hideMenu()));
    back->setFont(font);

    levelLabel = new QLabel(parent);
    levelLabel->setGeometry(parent->width()/2 - 50, 0, 200, 30);
    levelLabel->setText("LEVEL " + QString::number(level));
    levelLabel->setVisible(false);
    levelLabel->setStyleSheet("QLabel { background-color : black; color : white; }");
    font.setPointSize(20);
    font.setBold(true);
    levelLabel->setFont(font);

    //SCOREBOARD
    playerName = new QLabel(parent);
    playerName->setGeometry(0, 30, 100, 30);
    playerName->setText(name);
    playerName->setVisible(false);
    playerName->setStyleSheet("background-color: white");

    playerScoreLabel = new QLabel(parent);
    playerScoreLabel->setGeometry(100, 30, 100, 30);
    playerScoreLabel->setText(QString::number(gameScore));
    playerScoreLabel->setVisible(false);
    playerScoreLabel->setStyleSheet("background-color: gray");

    //READ THE SCOREBOARD FROM A TEXT FILE
    QFile c_file("../SpaceInvaders/scoreboard.txt");
    c_file.open(QIODevice::ReadOnly);
    int currentY = 65;
    QTextStream in(&c_file);
    while (!in.atEnd()) {
        QString l = in.readLine();
        l = l.trimmed();

        if (l.isEmpty()) {
            continue;
        }

        QString first = l.split(" ").at(0);
        QString second = l.split(" ").last();

        QLabel* pName = new QLabel(parent);
        pName->setGeometry(0, currentY, 100, 30);
        pName->setText(first);
        pName->setVisible(false);
        pName->setStyleSheet("background-color: white");
        playersNames.append(pName);

        QLabel* pScore = new QLabel(parent);
        pScore->setGeometry(100, currentY, 100, 30);
        pScore->setText(second);
        pScore->setVisible(false);
        pScore->setStyleSheet("background-color: gray");
        playersScores.append(pScore);

        currentY += 30;
    }
}

// called when game is paused or unpaused
void Menu::displayMenu(bool paused) {
    if (!paused) {
        closeButtons();
    } else {
        score->setVisible(true);
        controls->setVisible(true);
        speed->setVisible(true);
        back->setVisible(true);
    }
}

// helper, closes all the opened menus
void Menu::closeButtons() {
    if (controlsShown) {
        displayControls();
    }
    if (speedsShown) {
        displaySpeeds();
    }
    controls->setVisible(false);
    speed->setVisible(false);
    back->setVisible(false);
    score->setVisible(false);
    revealPlayerScore(false);
}

// helper function for OpenScore
void Menu::revealPlayerScore(bool open) {
    // recalculate playerScoreLabel
    playerScoreLabel->setText(QString::number(gameScore));
    playerName->setVisible(open);
    playerScoreLabel->setVisible(open);

    //DISPLAY OLD RESULTS
    for (QLabel* l: playersNames) {
        l->setVisible(open);
    }
    for (QLabel* l: playersScores) {
        l->setVisible(open);
    }
}

void Menu::showLevel() {
    levelLabel->setText("LEVEL " + QString::number(level));
    levelLabel->setVisible(true);
}

void Menu::hideLevel() {
    levelLabel->setVisible(false);
}

void Menu::openScore() {
    if (playerName->isVisible()) {
        revealPlayerScore(false);
    } else {
        revealPlayerScore(true);
    }
}

void Menu::highlightControl() {
    if (typeOfControl.startsWith("mouse")) {
        mouse->setStyleSheet("background-color: #01a5a3; color : #dce1ea;");
        keyboard->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
    } else {
        keyboard->setStyleSheet("background-color: #01a5a3; color : #dce1ea;");
        mouse->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
    }
}

void Menu::highlightSpeed() {
    if (frameRate == 60) {
        low->setStyleSheet("background-color: #01a5a3; color : #dce1ea;");
        medium->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
        high->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
    } else if (frameRate == 45){
        medium->setStyleSheet("background-color: #01a5a3; color : #dce1ea;");
        low->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
        high->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
    } else if (frameRate == 30) {
        high->setStyleSheet("background-color: #01a5a3; color : #dce1ea;");
        low->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
        medium->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
    }
}

void Menu::displayControls() {
    if (!controlsShown) {
        if (speedsShown) {
            displaySpeeds();
        }
        controls->setStyleSheet("background-color: #01a5a3; color : #dce1ea;");
        mouse->setVisible(true);
        keyboard->setVisible(true);
        controlsShown = true;
    }
    else {
        controls->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
        mouse->setVisible(false);
        keyboard->setVisible(false);
        controlsShown = false;
    }
}

void Menu::displaySpeeds() {
    if (!speedsShown) {
        if (controlsShown) {
            displayControls();
        }
        speed->setStyleSheet("background-color: #01a5a3; color : #dce1ea;");
        low->setVisible(true);
        medium->setVisible(true);
        high->setVisible(true);
        speedsShown = true;
    }
    else {
        speed->setStyleSheet("background-color: #dce1ea; color : #01a5a3;");
        low->setVisible(false);
        medium->setVisible(false);
        high->setVisible(false);
        speedsShown = false;
    }
}
}
