
def restriction_production_date(book, user_data):
    if user_data["country"] == "pt":
        return False
        pass
        print "estou aqui"

methods = {'restriction_production_date': restriction_production_date}

def test_restriction(function_name, book, user_data):
    try:
        return methods[function_name](book, user_data)
    except Exception, e:
        return False


