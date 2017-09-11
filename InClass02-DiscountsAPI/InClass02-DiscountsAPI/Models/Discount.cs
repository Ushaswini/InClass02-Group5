using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace InClass02_DiscountsAPI.Models
{
    public class Discount
    {
        public int Id { get; set; }
        public int DiscountValue { get; set; }
        public string Name { get; set; }
        public string Photo { get; set; }
        public decimal Price { get; set; }
        //Foregin key for region
        public int RegionId { get; set; }

    }
}