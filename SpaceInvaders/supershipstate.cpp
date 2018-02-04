#include "supershipstate.h"

namespace game {

SuperShipState::SuperShipState(QPixmap img) {
    image = img;
}

SuperShipState::~SuperShipState() {}

QList<Bullet*> SuperShipState::handleShoot(BulletBuilder builder) {
    QList<Bullet*> b;
    b.append(builder.build_bullet("super"));
    b.append(builder.build_bullet("superRotateLeft"));
    b.append(builder.build_bullet("superRotateRight"));
    return b;
}

const QPixmap& SuperShipState::get_image() {
    return image;
}

}
