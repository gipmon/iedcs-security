# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0002_account_citizen_card'),
    ]

    operations = [
        migrations.AlterField(
            model_name='account',
            name='citizen_card',
            field=models.FileField(upload_to=b'media/citizen_pub_certs'),
            preserve_default=True,
        ),
    ]
