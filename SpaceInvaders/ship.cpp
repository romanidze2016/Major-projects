#include "ship.h"
#include "bullet.h"
#include "config.h"

namespace game {
Ship::Ship(QPixmap image, double scale, int x, int y)
        : Base(image, scale, x, y - image.height() * scale,
                  Config::getInstance()->get_SCALEDWIDTH(),
                  Config::getInstance()->get_SCALEDWIDTH(), 0),
          velocity(10), bullet_velocity(15), builder(bullet_velocity, *this, "laser", true) {
    // adjust the image size according to scale
    this->set_image(this->get_image().scaledToWidth(this->get_image().width() * scale));

    // readjust (x,y) since the image was resized
    boundaryX = Config::getInstance()->get_SCALEDWIDTH() - this->get_image().width();
    boundaryY = Config::getInstance()->get_SCALEDHEIGHT() - this->get_image().height();

    set_x(x);
    set_y(y);

    QPixmap super;
    super.load(":/Images/superShip.png");
    normalState = new NormalShipState(this->get_image());
    superState = new SuperShipState(super.scaledToWidth(this->get_image().width()));
    currentState = normalState;
}

// A SHIP CAN MOVE LEFT, RIGHT AND SHOOT
QList<Bullet*> Ship::shoot() {
    return currentState->handleShoot(builder);
}

// Setters
void Ship::move_left() {
    set_x(get_x() - velocity);
}

void Ship::move_right() {
    set_x(get_x() + velocity);
}

void Ship::setNormalState() {
    currentState = normalState;
}

void Ship::setSuperState() {
    currentState = superState;
}

const QPixmap& Ship::get_ship_image() {
    this->set_image(currentState->get_image());
    return this->get_image();
}

Ship::~Ship() {
    delete normalState;
    delete superState;
}
}
