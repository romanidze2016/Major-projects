#include "normalshipstate.h"

namespace game {

NormalShipState::NormalShipState(QPixmap img) {
    image = img;
}

NormalShipState::~NormalShipState() {}

QList<Bullet*> NormalShipState::handleShoot(BulletBuilder builder) {
    QList<Bullet*> b;
    b.append(builder.build_bullet("laser"));
    return b;
}

const QPixmap& NormalShipState::get_image() {
    return image;
}

}
