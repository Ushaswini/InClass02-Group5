using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using InClass02_DiscountsAPI.Models;

namespace InClass02_DiscountsAPI.Controllers
{
    public class DiscountsController : ApiController
    {
        private DiscountsAPIContext db = new DiscountsAPIContext();

        // GET: api/Discounts
        public IQueryable<Discount> GetDiscounts()
        {
            return db.Discounts;
        }

        //GET: api/Discounts/Region/1
        public IQueryable<Discount> GetDiscountsForRegionId(string regionName)
        {
            var id = (from r in db.Regions where r.RegionName == regionName select r.Id).FirstOrDefault();
            var result = from d in db.Discounts where d.RegionId == (id) select d;
            return result;
        }

        // GET: api/Discounts/5
        [ResponseType(typeof(Discount))]
        public async Task<IHttpActionResult> GetDiscount(int id)
        {
            Discount discount = await db.Discounts.FindAsync(id);
            
            if (discount == null)
            {
                return NotFound();
            }

            return Ok(discount);
        }

        // PUT: api/Discounts/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutDiscount(int id, Discount discount)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != discount.Id)
            {
                return BadRequest();
            }

            db.Entry(discount).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!DiscountExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Discounts
        [ResponseType(typeof(Discount))]
        public async Task<IHttpActionResult> PostDiscount(Discount discount)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Discounts.Add(discount);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = discount.Id }, discount);
        }

        // DELETE: api/Discounts/5
        [ResponseType(typeof(Discount))]
        public async Task<IHttpActionResult> DeleteDiscount(int id)
        {
            Discount discount = await db.Discounts.FindAsync(id);
            if (discount == null)
            {
                return NotFound();
            }

            db.Discounts.Remove(discount);
            await db.SaveChangesAsync();

            return Ok(discount);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool DiscountExists(int id)
        {
            return db.Discounts.Count(e => e.Id == id) > 0;
        }
    }
}