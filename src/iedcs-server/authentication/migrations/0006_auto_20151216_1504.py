# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0005_account_citizen_card_sn'),
    ]

    operations = [
        migrations.RenameField(
            model_name='account',
            old_name='citizen_card_sn',
            new_name='citizen_card_serial_number',
        ),
    ]
