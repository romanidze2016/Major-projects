#ifndef MENU_H
#define MENU_H

#include <QLabel>
#include <QPushButton>
#include <QComboBox>
#include <QList>
namespace game {
class Menu {
public:
    Menu(QWidget* parent, QString name, int& playerScore, int& currentLevel, int& numOfFrames, QString& controlType, QList<QPair<QString, int>> scores);
    ~Menu();
    void displayMenu(bool paused);
    void openScore();
    void hideLevel();
    void showLevel();
    void highlightControl();
    void highlightSpeed();
    void displayControls();
    void displaySpeeds();

private:
    void makeButtons(QWidget* parent, QString name);
    void closeButtons();  // if any buttons are left open, close them
    int& gameScore;
    int& level;
    int& frameRate;
    QString& typeOfControl;

    QPushButton* score;

    QPushButton* speed;
    bool speedsShown = false;
    QPushButton* low;
    QPushButton* medium;
    QPushButton* high;

    QPushButton* controls;
    bool controlsShown = false;
    QPushButton* mouse;
    QPushButton* keyboard;

    QPushButton* back;

    QLabel* playerName;
    QString playerNameString;
    QLabel* playerScoreLabel;
    QList<QLabel*> playersNames;
    QList<QLabel*> playersScores;
    QLabel* levelLabel;
    void revealPlayerScore(bool open);
};
}
#endif  // SCOREBUTTON_H
