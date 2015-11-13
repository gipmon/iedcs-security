
def restriction_country(book, device):
    if device.country == "PT":
        return False
    elif device.country == "NL":
        return False
    else:
        return True


def restriction_hour(book, device):
    if int(device.time.split(":")[0]) == 15:
        return False
    else:
        return True

methods = {'restriction_country': restriction_country, 'restriction_hour': restriction_hour}


def test_restriction(function_name, book, device):
    try:
        return methods[function_name](book, device)
    except Exception, e:
        return False


